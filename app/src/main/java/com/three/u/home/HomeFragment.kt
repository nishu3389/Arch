package com.three.u.home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.style.ImageSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.binaryfork.spanny.Spanny
import com.google.gson.Gson
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import com.three.u.R
import com.three.u.activity.MainBoardActivity
import com.three.u.base.*
import com.three.u.common.IViewPagerClickListener
import com.three.u.databinding.FragmentHomeBinding
import com.three.u.model.request.RequestForgotPassword
import com.three.u.model.response.*
import com.three.u.util.Prefs
import com.three.u.util.RoundedCornersTransformation
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted
import com.vistrav.pop.Pop
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


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

        /*mViewModel.callAdvApi().observe(viewLifecycleOwner, Observer {

        })*/
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainBoardActivity).title = context?.titleWithLogo(R.string.home)
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
