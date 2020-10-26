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
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.model.request.RequestSavePayment
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.util.Prefs

class SubscriptionFragment : BaseFragment() {
    var sku = "product_subscription"
//    var sku = "android.test.purchased"
//    var sku = "android.test.canceled"

    private lateinit var billingClient: BillingClient
    private val skuList = listOf(sku)

    lateinit var mViewModel: SubscriptionViewModel
    lateinit var mBinding: FragmentSubscriptionBinding
    var onClickHandler = ClickHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    private fun setupBillingClient() {

        billingClient = BillingClient.newBuilder(requireContext())
            .enablePendingPurchases()
            .setListener { billingResult, purchases ->

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
                            if(!billingResult.debugMessage.isEmptyy())
                                billingResult.debugMessage.toast()
                        }
                    }

                }
            }
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is setup successfully
                    clearPurchases()
                    loadAllSKUs()
                }
            }

            override fun onBillingServiceDisconnected() {
//                "onBillingServiceDisconnected".toast()
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.

            }
        })

    }

    private fun loadAllSKUs() = if (billingClient.isReady) {
        val params = SkuDetailsParams
            .newBuilder()
            .setSkusList(skuList)
            .setType(BillingClient.SkuType.SUBS)
            .build()
        billingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            // Process the result.
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList.isNotEmpty()) {
                for (skuDetails in skuDetailsList) {
                    //this will return both the SKUs from Google Play Console
                    if (skuDetails.sku == sku)
                        mBinding.tvPurchase?.push()?.setOnClickListener {
                            val billingFlowParams = BillingFlowParams
                                .newBuilder()
                                .setSkuDetails(skuDetails)
                                .build()
                            billingClient.launchBillingFlow(activity, billingFlowParams)
                        }
                }
            }
        }

    } else {
        println("Billing Client not ready")
    }

    fun clearPurchases() {
        val purchasesResult: PurchasesResult =
            billingClient.queryPurchases(BillingClient.SkuType.SUBS)
        for (sourcePurchase in purchasesResult.purchasesList) {
            if (sourcePurchase != null) {
                val listener: ConsumeResponseListener = object : ConsumeResponseListener {
                    override fun onConsumeResponse(
                        billingResult: BillingResult?,
                        purchaseToken: String?
                    ) {
//                        billingResult.toString().toast()
                    }
                }
                val build =
                    ConsumeParams.newBuilder().setPurchaseToken(sourcePurchase.purchaseToken)
                        .setDeveloperPayload(sourcePurchase.developerPayload).build()
                billingClient.consumeAsync(build, listener)
            } else {
                println("null")
            }
        }
    }

    private fun acknowledgePurchase() {
        val receiptData = Gson().fromJson(Prefs.get().SUBS_DATA, ReceiptData::class.java)
        val params = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(receiptData.purchaseToken).build()

        billingClient.acknowledgePurchase(params) { _ ->
//          val receiptData = Gson().fromJson(purchase.originalJson, ReceiptData::class.java)
            mViewModel.requestSavePayment.set(RequestSavePayment(receiptData, sku))
            mViewModel.callSavePaymentApi().observe(viewLifecycleOwner, Observer {
                Prefs.get().SUBS_DATA = ""
                commonCallbacks?.showAlertDialog(
                    it.message,
                    DialogInterface.OnClickListener { _, _ ->
        findNavController().popBackStack(R.id.HomeFragment, false)
                    })
            })
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
//        activity?.finish()
//        findNavController().popBackStack(R.id.HomeFragment, false)
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

    }


}
