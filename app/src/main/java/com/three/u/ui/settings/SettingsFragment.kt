package com.three.u.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.three.u.R
import com.three.u.ui.activity.HomeActivity
import com.three.u.base.*
import com.three.u.databinding.FragmentSettingsBinding
import com.three.u.model.request.RequestForgotPassword
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted

class SettingsFragment : BaseFragment() {

    lateinit var dialog : AlertDialog
    lateinit var mViewModel: SettingsViewModel
    lateinit var mBinding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel

        }

        /*mViewModel.callAdvApi().observe(viewLifecycleOwner, Observer {

        })*/
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).title = context?.titleWithLogo(R.string.home)
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

        }

        fun myProfile(){
            navigate(R.id.ProfileShowFragment)
        }

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
