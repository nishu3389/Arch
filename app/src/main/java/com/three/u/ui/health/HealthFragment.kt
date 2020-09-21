package com.three.u.ui.health

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.three.u.R
import com.three.u.ui.activity.HomeActivity
import com.three.u.base.*
import com.three.u.databinding.FragmentAddWeightBinding
import com.three.u.databinding.FragmentHealthBinding
import com.three.u.databinding.FragmentHomeBinding
import com.three.u.model.request.RequestForgotPassword
import com.three.u.ui.health.weight.add_weight.AddWeightFragment
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted
import com.three.u.views.CommonTextView


class HealthFragment : BaseFragment() {

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

        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("")
//        (activity as HomeActivity).highlightHomeTab()
        setupViewPager()
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
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(HealthViewModel::class.java)
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
                    return AddWeightFragment()
                }
                2 -> {
                    return AddWeightFragment()
                }
                else -> {
                    return AddWeightFragment()
                }
            }
        }

    }

}
