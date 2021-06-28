package com.architecture.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.architecture.R
import com.architecture.base.*
import com.architecture.databinding.FragmentLoginBinding
import com.architecture.model.response.ResponseLogin
import com.architecture.networking.Api.PRIVACY_POLICY
import com.architecture.networking.Api.TERMS
import com.architecture.ui.activity.HomeActivity
import com.architecture.util.Prefs
import com.architecture.util.Validator
import com.architecture.util.permission.DeviceRuntimePermission
import com.architecture.util.permission.IPermissionGranted

class LoginFragment : BaseFragment(), IPermissionGranted {
    lateinit var mBinding: FragmentLoginBinding
    lateinit var mViewModel: LoginViewModel

    private val TAG = "LoginFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewModel = mViewModel
            clickHandler = ClickHandler()
        }
        return mBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).setPermissionGranted(this)
        permissionDenied(DeviceRuntimePermission.REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION)

        setClickable(mBinding.tvTerms, "Terms of Service", { TERMS.openInBrowser() })
        setClickable(mBinding.tvTerms, "Privacy Policy", { PRIVACY_POLICY.openInBrowser() })
    }

    private fun setupViewModel() {
        mViewModel = androidx.lifecycle.ViewModelProvider(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(LoginViewModel::class.java)
    }


    inner class ClickHandler {
        fun onClickLogin() {
            commonCallbacks?.hideKeyboard()

            navigateToHome()
/*
             if(isLoginValid())
                 mViewModel.callLoginApi().observe(viewLifecycleOwner, Observer {
                     if (it.data != null) {
                         Prefs.get().loginData = it.data
                         navigateToHome()
                     }
                 })
*/

        }


    }

    private fun navigateToHome() {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity?.startActivity(intent)
        activity?.finish()
    }

    override fun onApiRequestFailed(apiUrl: String, errCode: Int, errorMessage: String): Boolean {
        errorMessage.showError()
        return true
    }

    fun isLoginValid(): Boolean {

        var email = mBinding.edtEmail.get()
        var password = mBinding.edtPass.get()

        when {
            TextUtils.isEmpty(email) -> {
                mViewModel.errEmail.set(MainApplication.get().getString(R.string.err_email_missing))
                mViewModel.errEmail.get()?.showWarning()
                return false
            }

            !Validator.isEmailValid(email, mViewModel.errEmail) -> {
                return false
            }

            !Validator.isPasswordValidLogin(password, mViewModel.errPassword) -> {
                return false
            }

            else -> return true
        }
    }

    override fun permissionGranted(requestCode: Int) {

    }

    override fun permissionDenied(requestCode: Int) {

    }

    override fun handlingBackPress(): Boolean {
        activity?.finish()
        return true
    }

}