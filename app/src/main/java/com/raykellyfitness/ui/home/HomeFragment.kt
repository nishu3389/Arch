package com.raykellyfitness.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.raykellyfitness.R
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentHomeBinding
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.networking.Api
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.util.permission.DeviceRuntimePermission
import com.raykellyfitness.util.permission.IPermissionGranted


class HomeFragment : BaseFragment() {

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
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(HomeViewModel::class.java)
        mViewModel.requestForgotPassword.set(RequestForgotPassword())
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


}
