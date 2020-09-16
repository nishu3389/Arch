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

    override fun onStart() {
        super.onStart()
        Log.d(" lifecycle", "onStartFrag")
    }

    override fun onStop() {
        super.onStop()
        Log.d(" lifecycle", "onStopFrag")
    }

    override fun onResume() {
        super.onResume()
        Log.d(" lifecycle", "onResumeFrag")
    }

    override fun onPause() {
        super.onPause()
        Log.d(" lifecycle", "onPauseFrag")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel

        }

        mViewModel.callAdvApi().observe(viewLifecycleOwner, Observer {
            mBinding.mainLayout.visible()
        })
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainBoardActivity).title = context?.titleWithLogo(R.string.home)
        (activity as MainBoardActivity).unlockDrawer()

        mBinding.rrBusiness.push()?.setOnClickListener { ClickHandler().onBusinessCheckListClicked() }
        mBinding.rrResidential.push()?.setOnClickListener { ClickHandler().onResidentialCheckListClicked() }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(HomeViewModel::class.java)
        mViewModel.requestForgotPassword.set(RequestForgotPassword())
    }

    inner class ClickHandler : IPermissionGranted {
        fun onCheckListClicked() {
            findNavController().navigate(R.id.MyCheckListFragment)
        }

        fun onResidentialCheckListClicked() {
            findNavController().navigate(R.id.ResidentialChecklistFragment, bundleOf(Pair("from",true)))
            (activity as MainBoardActivity).highlightResidentialMenuItem()
        }

        fun onBusinessCheckListClicked() {
            findNavController().navigate(R.id.ResidentialChecklistFragment, bundleOf(Pair("from",false)))
            (activity as MainBoardActivity).highlightBusinessMenuItem()
        }

        fun serviceDirectoryItemClicked() {
            findNavController().navigate(R.id.ServicesDirectoryFragment)
        }

        fun boxesPackagingItemClicked() {
            var bundle: Bundle
            bundle = Bundle()
            bundle.putInt("BusinessCategoryID", 209)
            findNavController().navigate(R.id.ServicesDirectoryFragment, bundle)
        }

        fun utiliesItemClicked() {
            var bundle: Bundle
            bundle = Bundle()
            bundle.putInt("BusinessCategoryID", 210)
            findNavController().navigate(R.id.ServicesDirectoryFragment, bundle)

        }

        fun qrCodeClicked() {
            (activity as BaseActivity).setPermissionGranted(this)
            permissionDenied(DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA)

        }

        fun onServiceDirectoryClicked() {
            findNavController().navigate(R.id.ServicesDirectoryFragment)
        }

        fun browseItemClicked() {
            findNavController().navigate(R.id.BrowseItemListFragment)
        }

        fun selllGiveAwayClicked() {
            findNavController().navigate(R.id.SellOrGiveAwayFragment)
        }

        fun onClickSearch() {
            commonCallbacks?.hideKeyboard()
            if (mViewModel.validateInput()) {
                mViewModel.callForgotPasswordApi()
            }
        }

       /* fun advClicked() {
             mViewModel?.responseAdvertsementStrip?.advertLink.let {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(it)
                startActivityForResult(i,90)
            }
        }*/

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

    override fun onDestroyView() {
        super.onDestroyView()
        /* (requireActivity() as MainBoardActivity).supportActionBar!!.setBackgroundDrawable(
             ColorDrawable(Color.parseColor("#FFFFFF"))
         )*/
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
