package com.raykellyfitness.ui.subscription

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.*
import com.android.billingclient.api.Purchase.PurchasesResult
import com.google.gson.Gson
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentSubscriptionBinding
import com.raykellyfitness.model.request.ReceiptData
import com.raykellyfitness.model.request.RequestSavePayment
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.util.Constant.SKU
import com.raykellyfitness.util.Prefs

class SubscriptionFragment() : BaseFragment(), SubsCompleteListener {

    private var billingClient: BillingClient? = null
    private val skuList = listOf(SKU)

    lateinit var mViewModel: SubscriptionViewModel
    lateinit var mBinding: FragmentSubscriptionBinding
    var onClickHandler = ClickHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setObserver()
        checkUnSyncedSubsData()
    }

    private fun checkUnSyncedSubsData() {
        if(!Prefs.get().SUBS_DATA.isEmptyy()){
            val receiptData = Gson().fromJson(Prefs.get().SUBS_DATA, ReceiptData::class.java)
            mViewModel.requestSavePayment.set(RequestSavePayment(receiptData, receiptData.productId))
            mViewModel.callSavePaymentApi().observe(this, Observer {
                Prefs.get().SUBS_DATA = ""
                commonCallbacks?.showAlertDialog(
                    it.message,
                    DialogInterface.OnClickListener { _, _ -> findNavController().popBackStack(R.id.HomeFragment, false) })
            })
        }
    }

    private fun setupBillingClient() {
        billingClient = MainApplication.get().getInAppBillingClient(this)
    }

    private fun loadAllSKUs() = if (billingClient?.isReady!!) {

        val params = SkuDetailsParams.newBuilder().setSkusList(skuList).setType(BillingClient.SkuType.SUBS).build()

        billingClient?.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            // Process the result.
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null && skuDetailsList?.isNotEmpty()) {
                for (skuDetails in skuDetailsList) {
                    //this will return both the SKUs from Google Play Console
                    if (skuDetails.sku == SKU) mBinding.tvPurchase?.push()?.setOnClickListener {
                        val billingFlowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build()
                        billingClient?.launchBillingFlow(requireActivity(), billingFlowParams)
                    }
                }
            }
        }

    } else {
        println("Billing Client not ready")
    }

    fun clearPurchases() {
        val purchasesResult: PurchasesResult? =
            billingClient?.queryPurchases(BillingClient.SkuType.SUBS)
        for (sourcePurchase in purchasesResult?.purchasesList!!) {
            if (sourcePurchase != null) {
                val listener: ConsumeResponseListener = object : ConsumeResponseListener {
                    override fun onConsumeResponse(p0: BillingResult, p1: String) {

                    }
                }
                //                val build = ConsumeParams.newBuilder().setPurchaseToken(sourcePurchase.purchaseToken)
                //                    .setDeveloperPayload(sourcePurchase.developerPayload).build()
                val build =
                    ConsumeParams.newBuilder().setPurchaseToken(sourcePurchase.purchaseToken)
                        .setPurchaseToken(sourcePurchase.sku).build()
                billingClient?.consumeAsync(build, listener)
            } else {
                println("null")
            }
        }
    }

    private fun acknowledgePurchase() {
        val receiptData = Gson().fromJson(Prefs.get().SUBS_DATA, ReceiptData::class.java)
        val params =
            AcknowledgePurchaseParams.newBuilder().setPurchaseToken(receiptData.purchaseToken)
                .build()

        billingClient?.acknowledgePurchase(params) { _ ->
            //          val receiptData = Gson().fromJson(purchase.originalJson, ReceiptData::class.java)
            mViewModel.requestSavePayment.set(RequestSavePayment(receiptData, SKU))
            mViewModel.callSavePaymentApi().observe(SubscriptionFragment@this, Observer {
                Prefs.get().SUBS_DATA = ""
                commonCallbacks?.showAlertDialog(it.message, DialogInterface.OnClickListener { _, _ ->
                    findNavController().popBackStack(R.id.HomeFragment, false)
                })
            })
        }

    }

    private fun setObserver() {
       /* mViewModel.responseSavePayment.observe(this, Observer {
            Prefs.get().SUBS_DATA = ""
            commonCallbacks?.showAlertDialog(it.message, DialogInterface.OnClickListener { _, _ ->
                findNavController().popBackStack(R.id.HomeFragment, false)
            })
        })*/
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentSubscriptionBinding.inflate(inflater, container, false).apply {
            clickHandler = onClickHandler
            viewModel = mViewModel

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("")
        (activity as HomeActivity).showBack(false)
        (activity as HomeActivity).showRightLogo(false)
        setupClicks()
        setupBillingClient()
    }

    override fun handlingBackPress(): Boolean {
        //                activity?.finish()
        (activity as HomeActivity).navController.popBackStack(R.id.HomeFragment, false)
        return true
    }

    private fun setupClicks() {

    }

    private fun setupViewModel() {
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(SubscriptionViewModel::class.java)
    }

    inner class ClickHandler {
        fun manageSubs() {
            "https://play.google.com/store/account/subscriptions".openInBrowser()
        }
    }

    override fun onSubsCompleted(billingResult: BillingResult, purchases: List<Purchase>?) {
        when {
            billingResult?.responseCode == BillingClient.BillingResponseCode.OK && !purchases.isEmptyy() -> {
                val purchaseData = purchases?.get(0)
                Prefs.get().SUBS_DATA = purchaseData?.originalJson.toString()
                acknowledgePurchase()
            }

            billingResult?.responseCode == BillingClient.BillingResponseCode.USER_CANCELED -> {
                /*"USER_CANCELED".toast()*/
            }

            else -> {
                if (billingResult?.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                    clearPurchases()
                    if (!billingResult.debugMessage.isEmptyy()) billingResult.debugMessage.toast()
                }
            }

        }
    }

    override fun onConnected(var1: BillingResult) {
        //        clearPurchases()
        loadAllSKUs()
    }

    override fun onDisconnected() {

    }

}