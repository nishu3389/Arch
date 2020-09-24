package com.three.u.ui.health

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.three.u.R
import com.three.u.ui.activity.HomeActivity
import com.three.u.base.*
import com.three.u.databinding.FragmentAddWeightBinding
import com.three.u.databinding.FragmentHealthBinding
import com.three.u.databinding.FragmentHomeBinding
import com.three.u.model.request.RequestForgotPassword
import com.three.u.model.request.ResponseAddBloodPressure
import com.three.u.model.request.ResponseAddBloodSugar
import com.three.u.model.request.ResponseAddWeight
import com.three.u.ui.health.bloodpressure.add_bloodpressure.AddBloodPressureFragment
import com.three.u.ui.health.bloodsugar.add_bloodsugar.AddBloodSugarFragment
import com.three.u.ui.health.weight.add_weight.AddWeightFragment
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted
import com.three.u.views.CommonTextView
import com.vistrav.pop.Pop


class HealthFragment : BaseFragment() {

    var listWeight = MutableLiveData<ResponseAddWeight>()
    var listBloodSugar = MutableLiveData<ResponseAddBloodSugar>()
    var listBloodPressure = MutableLiveData<ResponseAddBloodPressure>()

    var popup : AlertDialog? = null
    lateinit var dialog: AlertDialog
    lateinit var mViewModel: HealthViewModel
    lateinit var mBinding: FragmentHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHealthBinding.inflate(inflater, container, false).apply {
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
        setupViewPager()
        manageClicks()
        callInitialApis()
    }

    fun callInitialApis() {
        mViewModel.callWeightListApi().observe(viewLifecycleOwner, Observer {
            if(!it.data.isNullOrEmpty() && it.data!!.size>0) {
                listWeight.value = (it.data!!)
            }
        })
        mViewModel.callBloodSugarListApi().observe(viewLifecycleOwner, Observer {
            if(!it.data.isNullOrEmpty() && it.data!!.size>0) {
                listBloodSugar.value = (it.data!!)
            }
        })
        mViewModel.callBloodPressureListApi().observe(viewLifecycleOwner, Observer {
            if(!it.data.isNullOrEmpty() && it.data!!.size>0) {
                listBloodPressure.value = (it.data!!)
            }
        })
    }

    private fun initWork() {
        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).showRightLogo(true)
        (activity as HomeActivity).setTitle("")
    }

    private fun manageClicks() {
        (activity as HomeActivity).mBinding.imgRight.push()?.setOnClickListener {

            when(mBinding.viewPager.currentItem){
                0 -> {
                    mViewModel.callWeightListApi().observe(viewLifecycleOwner, Observer {
                        if(!it.data.isNullOrEmpty() && it.data!!.size>0)
                            showWeightDialog(it.data!!)
                        else
                            "No records available".showWarning()
                    })
                }
                1 -> {
                    mViewModel.callBloodSugarListApi().observe(viewLifecycleOwner, Observer {
                        if(!it.data.isNullOrEmpty() && it.data!!.size>0)
                            showBloodSugarDialog(it.data!!)
                        else
                            "No records available".showWarning()
                    })
                }
                2 -> {
                    mViewModel.callBloodPressureListApi().observe(viewLifecycleOwner, Observer {
                        if(!it.data.isNullOrEmpty() && it.data!!.size>0)
                            showBloodPressureDialog(it.data!!)
                        else
                            "No records available".showWarning()
                    })
                }
            }

        }
    }

    private fun showBloodPressureDialog(data: ResponseAddBloodPressure) {
        popup = Pop.on(activity)
            .with()
            .cancelable(false)
            .layout(R.layout.blood_pressure_list)
            .show {

                it?.findViewById<RecyclerView>(R.id.recycler).apply {
                    var adapter = BloodPressureListAdapter(R.layout.row_blood_pressure_list)
                    this!!.adapter = adapter
                    adapter.setNewItems(data)
                }

                it?.findViewById<ImageView>(R.id.img_cross).apply {
                    this?.push()?.setOnClickListener {
                        popup?.dismiss()
                    }
                }

            }

        popup?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun showBloodSugarDialog(data: ResponseAddBloodSugar) {
        popup = Pop.on(activity)
            .with()
            .cancelable(false)
            .layout(R.layout.blood_sugar_list)
            .show {

                it?.findViewById<RecyclerView>(R.id.recycler).apply {
                    var adapter = BloodSugarListAdapter(R.layout.row_blood_sugar_list)
                    this!!.adapter = adapter
                    adapter.setNewItems(data)
                }

                it?.findViewById<ImageView>(R.id.img_cross).apply {
                    this?.push()?.setOnClickListener {
                        popup?.dismiss()
                    }
                }

            }

        popup?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun showWeightDialog(data: ResponseAddWeight) {
        popup = Pop.on(activity)
            .with()
            .cancelable(false)
            .layout(R.layout.weight_list)
            .show {

               it?.findViewById<RecyclerView>(R.id.recycler).apply {
                   var adapter = WeightListAdapter(R.layout.row_weight_list)
                   this!!.adapter = adapter
                   adapter.setNewItems(data)
               }

                it?.findViewById<ImageView>(R.id.img_cross).apply {
                    this?.push()?.setOnClickListener {
                        popup?.dismiss()
                    }
                }

            }

        popup?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setupViewPager() {

        mBinding.viewPager.adapter = HealthPagerAdapter(childFragmentManager)

        mBinding.tvWeight.push()?.setOnClickListener {
            highlightTab(mBinding.tvWeight)
            mBinding.viewPager.currentItem = 0
        }
        mBinding.tvBloodSugar.push()?.setOnClickListener {
            highlightTab(mBinding.tvBloodSugar)
            mBinding.viewPager.currentItem = 1
        }
        mBinding.tvBloodPressure.push()?.setOnClickListener {
            highlightTab(mBinding.tvBloodPressure)
            mBinding.viewPager.currentItem = 2
        }

        mBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        highlightTab(mBinding.tvWeight)
                    }
                    1 -> {
                        highlightTab(mBinding.tvBloodSugar)
                    }
                    2 -> {
                        highlightTab(mBinding.tvBloodPressure)
                    }
                }
            }


            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun highlightTab(textView: CommonTextView) {
        requireContext().setTvColor(mBinding.tvWeight, R.color.black_color)
        requireContext().setTvColor(mBinding.tvBloodSugar, R.color.black_color)
        requireContext().setTvColor(mBinding.tvBloodPressure, R.color.black_color)

        requireContext().setTvBGColor(mBinding.tvWeight, R.color.transparent)
        requireContext().setTvBGColor(mBinding.tvBloodSugar, R.color.transparent)
        requireContext().setTvBGColor(mBinding.tvBloodPressure, R.color.transparent)

        requireContext().setTvColor(textView, R.color.white)
        requireContext().setTvBGColor(textView, R.drawable.health_tab_bg)

        if (textView == mBinding.tvBloodPressure) {
            mBinding.horizontal.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }
        if (textView == mBinding.tvWeight) {
            mBinding.horizontal.fullScroll(HorizontalScrollView.FOCUS_LEFT)
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(HealthViewModel::class.java)
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

    class HealthPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return AddWeightFragment()
                }
                1 -> {
                    return AddBloodSugarFragment()
                }
                else -> {
                    return AddBloodPressureFragment()
                }
            }
        }


    }

    override fun onDetach() {
        super.onDetach()
        (activity as HomeActivity).showRightLogo(false)
    }

}
