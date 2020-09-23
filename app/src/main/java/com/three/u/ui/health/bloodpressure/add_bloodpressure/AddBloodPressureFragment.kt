package com.three.u.ui.health.bloodpressure.add_bloodpressure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.three.u.R
import com.three.u.ui.activity.HomeActivity
import com.three.u.base.*
import com.three.u.databinding.FragmentAddBloodPressureBinding
import com.three.u.databinding.FragmentAddBloodSugarBinding
import com.three.u.databinding.FragmentAddWeightBinding
import com.three.u.databinding.FragmentHomeBinding
import com.three.u.model.request.RequestAddBloodPressure
import com.three.u.model.request.RequestAddWeight
import com.three.u.model.request.RequestForgotPassword
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted


class AddBloodPressureFragment : BaseFragment() {

    lateinit var dialog : AlertDialog
    lateinit var mViewModel: AddBloodPressureViewModel
    lateinit var mBinding: FragmentAddBloodPressureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentAddBloodPressureBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("")
        manageClicks()
    }

    private fun manageClicks() {
        mBinding.tvAdd.push()?.setOnClickListener {
            if(mViewModel.validateInput()){
                mViewModel.callAddBllodPressureApi().observe(viewLifecycleOwner, Observer {
                    if (it != null && it.responseCode == 200){
                        mViewModel.requestAddBloodPressure.set(RequestAddBloodPressure())
                        showSuccessBar(it.message)
                    }
                    else
                        showErrorBar(it.message)
                })
            }
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(AddBloodPressureViewModel::class.java)
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
