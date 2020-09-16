package com.three.u.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.three.u.R
import com.three.u.activity.MainBoardActivity
import com.three.u.base.*
import com.three.u.databinding.FragmentLoginBinding
import com.three.u.util.ParcelKeys
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
        setupOberserver()
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

        // TODO: 24-06-2020 temp code by avinash 
//        val get = mViewModel.requestLogin.get()
//        get?.Email =  "nishu@yopmail.com"
//        get?.Password = "123456"
    }

    private fun setupViewModel() {
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(LoginViewModel::class.java)
    }



    private fun setupOberserver() {
        mViewModel.responseLogin.observe(this, Observer {
            if (it.data != null) {

                Prefs.get().loginData = it.data
                Prefs.get().checkListPercent = it.data!!.checkCompletionPercentage!!
                if (it.data!!.isProfileCompeled == false) {
                    var bundle = bundleOf("isshowtop" to true)
                    findNavController().navigate(R.id.ProfileEditFragment, bundle)
                } else {
                    Prefs.get().isPRofileComplete=true
                    val intent: Intent = Intent(activity, MainBoardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    activity?.startActivity(intent)
                    activity?.finish()
                }
            }
        })
    }

    inner class ClickHandler {
        fun onClickLogin() {
            commonCallbacks?.hideKeyboard()

            startActivity(Intent(context, MainBoardActivity::class.java))
//            navigate(R.id.HomeFragment)

           /* if(isLoginValid()){
                if(!mBinding.edtEmail.get().isEmptyy() && mBinding.edtEmail.get().isNumber()){
                    mViewModel.requestLogin.get()?.PhoneNumber = mBinding.edtEmail.get()
                    mViewModel.requestLogin.get()?.Email = ""
                }
                else{
                    mViewModel.requestLogin.get()?.Email = mBinding.edtEmail.get()
                    mViewModel.requestLogin.get()?.PhoneNumber = ""
                }

                mViewModel.callLoginApi()
            }*/
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

    var isNumber : Boolean? = false
    fun isLoginValid(): Boolean {

        var email = mBinding.edtEmail.get()
        var password = mBinding.edtPass.get()

        isNumber = email.let { email.isNumber() }

        when {
            TextUtils.isEmpty(email) -> {
                mViewModel.errEmail.set(MainApplication.get().getString(R.string.err_email_or_phone_missing))
                mViewModel.errEmail.get()?.showWarning()
                return false
            }

            (isNumber != null && isNumber!!) && !Validator.isPhoneValid(email, mViewModel.errEmail) -> {
                return false
            }

            isNumber == false && !Validator.isEmailOrNumberValid(email, mViewModel.errEmail) -> {
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
