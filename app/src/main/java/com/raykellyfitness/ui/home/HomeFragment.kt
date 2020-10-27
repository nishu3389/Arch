package com.raykellyfitness.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.*
import com.google.gson.Gson
import com.raykellyfitness.R
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentHomeBinding
import com.raykellyfitness.model.request.ReceiptData
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.model.request.RequestSavePayment
import com.raykellyfitness.networking.Api
import com.raykellyfitness.ui.subscription.SubsCompleteListener
import com.raykellyfitness.util.Constant.SKU
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.util.permission.DeviceRuntimePermission
import com.raykellyfitness.util.permission.IPermissionGranted


class HomeFragment : BaseFragment(), SubsCompleteListener {

    lateinit var dialog : AlertDialog
    lateinit var mViewModel: HomeViewModel
    lateinit var mBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("")
        (activity as HomeActivity).highlightHomeTab()
        manageClicks()
        checkSubsData()
    }

    private fun checkSubsData() {
        if(!Prefs.get().SUBS_DATA.isEmptyy()){
            val receiptData = Gson().fromJson(Prefs.get().SUBS_DATA, ReceiptData::class.java)
            mViewModel.requestSavePayment.set(RequestSavePayment(receiptData, receiptData.productId))
            mViewModel.callSavePaymentApi().observe(viewLifecycleOwner, Observer {
                Prefs.get().SUBS_DATA = ""
                commonCallbacks?.showAlertDialog(
                    it.message,
                    DialogInterface.OnClickListener { _, _ -> })
            })
        }

    }

    override fun onResume() {
        super.onResume()

        checkIfSubscribed()
    }

    private var billingClient: BillingClient? = null

    private fun checkIfSubscribed() {
        billingClient = MainApplication.get().getInAppBillingClient(this)
    }

    private fun manageClicks() {
        mBinding.rrMeal.setOnLongClickListener {
            navigate(R.id.SubscriptionFragment)
            false
        }
        mBinding.rrMeal.push()?.setOnClickListener {
            Prefs.get().SHUTDOWN = ""
            navigate(R.id.TipsAndTricksFragment, Pair("type", Api.POST_TYPE_MEAL))
        }
        mBinding.rrTips.push()?.setOnClickListener {
            navigate(R.id.TipsAndTricksFragment, Pair("type", Api.POST_TYPE_TIPS))
        }
        mBinding.rrExercise.push()?.setOnClickListener {
            navigate(R.id.TipsAndTricksFragment, Pair("type", Api.POST_TYPE_EXERCISE))
        }
       mBinding.rrMotivation.push()?.setOnClickListener {
            navigate(R.id.TipsAndTricksFragment, Pair("type", Api.POST_TYPE_MOTIVATION))
        }
       mBinding.rrBlogs.push()?.setOnClickListener {
            navigate(R.id.TipsAndTricksFragment, Pair("type", Api.POST_TYPE_BLOG))
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(HomeViewModel::class.java)
    }

    inner class ClickHandler : IPermissionGranted {


        override fun permissionGranted(requestCode: Int) {
            when (requestCode) {

                DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> {
                    findNavController().navigate(R.id.ScanQRCodeFragment)
                }
            }
        }

        override fun permissionDenied(requestCode: Int) {
            when (requestCode) {

                DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> (activity as BaseActivity).checkAndAskPermission(
                    DeviceRuntimePermission(
                        DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA,
                        null
                    )
                )
            }
        }
    }

    override fun onSubsCompleted(var1: BillingResult, var2: List<Purchase>?) {

    }

    override fun onConnected(var1: BillingResult) {
        billingClient?.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP) { p0, purchasesList ->
            if (!purchasesList.isEmptyy()) {
                var isPurchased = false
                purchasesList?.forEach {
                    if (it.sku.equals(SKU)) isPurchased = true
                }

                if (!isPurchased) navigate(R.id.SubscriptionFragment)
            }
        }

        val purchasesList = billingClient?.queryPurchases(BillingClient.SkuType.INAPP)?.purchasesList
        val billingResult = billingClient?.queryPurchases(BillingClient.SkuType.INAPP)?.billingResult
        val responseCode = billingResult?.responseCode
        val debugMessage = billingResult?.debugMessage
    }

    override fun onDisconnected() {

    }


}
