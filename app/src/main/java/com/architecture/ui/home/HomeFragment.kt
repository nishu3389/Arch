package com.architecture.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.architecture.R
import com.architecture.base.*
import com.architecture.databinding.FragmentHomeBinding
import com.architecture.ui.activity.HomeActivity
import com.architecture.util.permission.DeviceRuntimePermission
import com.architecture.util.permission.IPermissionGranted

class HomeFragment : BaseFragment() {

    lateinit var dialog: AlertDialog
    lateinit var mViewModel: HomeViewModel
    lateinit var mBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).showBack(false)
        (activity as HomeActivity).showLogo(false)
        (activity as HomeActivity).setTitle(R.string.home.get())


        // send notification
        "Some message".sendNotification()

        // show log
        "Some message".log()

        // show log with specific key
        "Some message".log("key")

        // show toast
        "Some message".toast()

        // if string is empty
        "Some message".isEmptyy()

        // get string by id
        R.string.app_name.get()

        // open some url in default browser
//        "https://www.google.com/".openInBrowser()

        // show success message
        "Congrates".showSuccess()

        // show error message
        "Opps...".showError()

        // show warning message
        "Beware".showWarning()

        // push animation while click on some view
        mBinding.tv.push()?.setOnClickListener { }

        // visible any view
        mBinding.tv.visible()

        // hide any view
        mBinding.tv.gone()

        // set image from url to imageview
        mBinding.img.set(requireContext(), "https://picsum.photos/200")

        // set image from drawable to imageview
        mBinding.img.set(requireContext(), R.drawable.back_arrow)

        // set color
        mBinding.tv.color(R.color.black)

        // check if list is empty
        var list = arrayListOf("a", "b", "c")
        list.isEmptyy()

        // get a random element from list
        list.random()

        // share app
        requireContext().shareApp()

        // addTextChangedListener on edittext
        mBinding.edt.onTextChange { str ->
            // do something in afterTextChanged here
            // or use the str
            str.log()
        }

        // set right drawable click listener
        mBinding.edt.onRightDrawableClick {
            // do something on drawable right click
            "Clicked".toast()
        }

    }

    private fun setupViewModel() {
        mViewModel = ViewModelProvider(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(HomeViewModel::class.java)
    }

    inner class ClickHandler : IPermissionGranted {

        override fun permissionGranted(requestCode: Int) {
            when (requestCode) {

                DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> {
                }
            }
        }

        override fun permissionDenied(requestCode: Int) {
            when (requestCode) {

                DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> (activity as BaseActivity).checkAndAskPermission(
                    DeviceRuntimePermission(DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA,
                                            null))
            }
        }
    }


}
