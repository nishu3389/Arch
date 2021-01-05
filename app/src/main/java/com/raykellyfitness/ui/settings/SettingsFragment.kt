package com.raykellyfitness.ui.settings

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
import com.raykellyfitness.databinding.FragmentSettingsBinding
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.util.permission.DeviceRuntimePermission
import com.raykellyfitness.util.permission.IPermissionGranted

class SettingsFragment : BaseFragment() {

    lateinit var dialog : AlertDialog
    lateinit var mViewModel: SettingsViewModel
    lateinit var mBinding: FragmentSettingsBinding
    var onClickHandler = ClickHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false).apply {
            clickHandler = onClickHandler
            viewModel = mViewModel

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("")
        setupClicks()
    }

    private fun setupClicks() {
        mBinding.rrMyProfile.push()?.setOnClickListener {
            onClickHandler.myProfile()
        }
        mBinding.rrChangePassword.push()?.setOnClickListener {
            onClickHandler.changePassword()
        }
        mBinding.rrLogout.push()?.setOnClickListener {
            onClickHandler.logout()
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(SettingsViewModel::class.java)
        mViewModel.requestForgotPassword.set(RequestForgotPassword())
    }

    inner class ClickHandler : IPermissionGranted {

        fun logout(){
            (activity as HomeActivity).requestLogout()
        }

        fun changePassword(){
            navigate(R.id.ChangePasswordFragment)
        }

        fun myProfile(){
            navigate(R.id.ProfileShowFragment)
        }

        override fun permissionGranted(requestCode: Int) {
            when (requestCode) {

                DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> {
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
