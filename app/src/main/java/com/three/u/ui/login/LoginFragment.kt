package com.three.u.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.three.u.R
import com.three.u.ui.activity.HomeActivity
import com.three.u.base.*
import com.three.u.databinding.FragmentLoginBinding
import com.three.u.util.Prefs
import com.three.u.util.Validator
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted

class LoginFragment : BaseFragment(), IPermissionGranted {
    lateinit var mBinding: FragmentLoginBinding
    lateinit var mViewModel: LoginViewModel

    private val TAG = "LoginFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        // TODO: 24-06-2020 temp code by avinash 
        val get = mViewModel.requestLogin.get()
        get?.Email =  "participant@mailinator.com"
        get?.Password = "123456"

        setClickable(mBinding.tvTerms, "Terms of Service", { "Terms Clicked".toast() })
        setClickable(mBinding.tvTerms, "Privacy Policy", { "Privacy Clicked".toast() })
    }

    private fun setupViewModel() {
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(LoginViewModel::class.java)
    }




    inner class ClickHandler {
        fun onClickLogin() {
            commonCallbacks?.hideKeyboard()

             if(isLoginValid())
                 mViewModel.callLoginApi().observe(viewLifecycleOwner, Observer {
                     if (it.data != null) {
                         Prefs.get().loginData = it.data
                         val intent = Intent(activity, HomeActivity::class.java)
                         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                         activity?.startActivity(intent)
                         activity?.finish()
                     }
                 })

        }

        fun onClickForgotPassword() {
            commonCallbacks?.hideKeyboard()
            findNavController().navigate(R.id.ForgotPasswordFragment)
        }

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

}
