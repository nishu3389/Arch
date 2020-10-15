package com.raykellyfitness.ui.subscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.*
import com.android.billingclient.api.Purchase.PurchasesResult
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentSubscriptionBinding
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.ui.activity.HomeActivity


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
                    billingResult?.responseCode == BillingClient.BillingResponseCode.OK && purchases != null ->{

                        purchases.toString().toast()

                        for (purchase in purchases)
                            acknowledgePurchase(purchase.purchaseToken)
                    }

                    billingResult?.responseCode == BillingClient.BillingResponseCode.USER_CANCELED -> "USER_CANCELED".toast()
                    else -> {
                        if(billingResult.debugMessage.equals("Item is already owned.", true)){
                            clearPurchases()
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
                "onBillingServiceDisconnected".toast()
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

    fun clearPurchases(){
        val purchasesResult: PurchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
        for (sourcePurchase in purchasesResult.purchasesList) {
            if (sourcePurchase != null) {
                val listener: ConsumeResponseListener = object : ConsumeResponseListener {
                    override fun onConsumeResponse(billingResult: BillingResult?, purchaseToken: String?) {
                        billingResult.toString().toast()
                    }
                }
                val build = ConsumeParams.newBuilder().setPurchaseToken(sourcePurchase.purchaseToken).setDeveloperPayload(sourcePurchase.developerPayload).build()
                billingClient.consumeAsync(build, listener)
            } else {
                println("null")
            }
        }
    }

    private fun acknowledgePurchase(purchaseToken: String) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchaseToken)
            .build()
        billingClient.acknowledgePurchase(params) { billingResult ->
            val responseCode = billingResult.responseCode
            val debugMessage = billingResult.debugMessage
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
        findNavController().popBackStack(R.id.HomeFragment, false)
        return true
    }

    private fun setupClicks() {

    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(
            this,
            MyViewModelProvider(commonCallbacks as AsyncViewController)
        ).get(SubscriptionViewModel::class.java)
        mViewModel.requestForgotPassword.set(RequestForgotPassword())
    }

    inner class ClickHandler {

        fun myProfile(){

        }

    }



}
