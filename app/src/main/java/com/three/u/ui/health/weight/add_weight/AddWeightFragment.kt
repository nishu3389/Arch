package com.three.u.ui.health.weight.add_weight

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.three.u.R
import com.three.u.base.*
import com.three.u.databinding.FragmentAddWeightBinding
import com.three.u.model.request.RequestAddWeight
import com.three.u.model.request.ResponseAddWeight
import com.three.u.model.request.ResponseAddWeightItem
import com.three.u.ui.activity.HomeActivity
import com.three.u.ui.health.HealthFragment
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted
import java.util.*

class AddWeightFragment : BaseFragment() {

    lateinit var dialog : AlertDialog
    lateinit var mViewModel: AddWeightViewModel
    lateinit var mBinding: FragmentAddWeightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAddWeightBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWork()
        otherWork()
    }

    private fun otherWork() {
        manageClicks()
        var healthFragment = this.parentFragment as HealthFragment
        val listWeight = healthFragment.listWeight.get()

        setChartData(listWeight)

    }

    private fun setChartData(listWeight: ResponseAddWeight?) {

    }



    private fun initWork() {
        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("")
    }

    private fun manageClicks() {

        mBinding.tvAdd.push()?.setOnClickListener {
            if(mViewModel.validateInput()){
                mViewModel.callAddWeightApi().observe(viewLifecycleOwner, Observer {
                    if (it != null && it.responseCode == 200) {
                        mViewModel.requestAddWeight.set(RequestAddWeight())
                        showSuccessBar(it.message)
                    } else
                        showErrorBar(it.message)
                })
            }
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(
            this,
            MyViewModelProvider(commonCallbacks as AsyncViewController)
        ).get(AddWeightViewModel::class.java)
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
