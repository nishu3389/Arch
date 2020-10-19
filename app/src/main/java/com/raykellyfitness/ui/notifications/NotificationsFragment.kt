package com.raykellyfitness.ui.notifications

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
import com.raykellyfitness.databinding.FragmentNotificationsBinding
import com.raykellyfitness.databinding.FragmentSettingsBinding
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.util.permission.DeviceRuntimePermission
import com.raykellyfitness.util.permission.IPermissionGranted

class NotificationsFragment : BaseFragment() {

    lateinit var dialog : AlertDialog
    lateinit var mViewModel: NotificationsViewModel
    lateinit var mBinding: FragmentNotificationsBinding
    var onClickHandler = ClickHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentNotificationsBinding.inflate(inflater, container, false).apply {
            clickHandler = onClickHandler
            viewModel = mViewModel

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).showBack(false)
        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("Notifications")
        setupClicks()
    }

    private fun setupClicks() {

    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(NotificationsViewModel::class.java)
        mViewModel.requestForgotPassword.set(RequestForgotPassword())
    }

    inner class ClickHandler {

    }


}
