package com.three.u.ui.forgot_password

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.three.u.R
import com.three.u.base.*
import com.three.u.databinding.FragmentForgotPasswordBinding
import com.three.u.model.request.RequestForgotPassword
import com.three.u.util.Validator


class ForgotPasswordFragment : BaseFragment() {

    lateinit var mViewModel: ForgotPasswordViewModel
    lateinit var mBinding: FragmentForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentForgotPasswordBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        commonCallbacks?.setupToolBar(mBinding.toolbarLayout, true, "")
        commonCallbacks?.setupActionBarWithNavController(mBinding.toolbarLayout.toolbar)
    }

    private fun setupViewModel() {
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(
                    ForgotPasswordViewModel::class.java
                )
        mViewModel.requestForgotPassword.set(RequestForgotPassword())
    }

    private fun setupObserver() {

    }

    inner class ClickHandler {

        fun onClickSubmit() {
            commonCallbacks?.hideKeyboard()
            if (isValid()) {
                if(!mBinding.edtEmailOrPhone.get().isEmptyy() && mBinding.edtEmailOrPhone.get().isNumber()){
                    mViewModel.requestForgotPassword.get()?.PhoneNumber = mBinding.edtEmailOrPhone.get()
                    mViewModel.requestOTP.get()?.PhoneNumber = mBinding.edtEmailOrPhone.get()
                    mViewModel.requestForgotPassword.get()?.email = ""
                }
                else{
                    mViewModel.requestForgotPassword.get()?.email = mBinding.edtEmailOrPhone.get()
                    mViewModel.requestForgotPassword.get()?.PhoneNumber = ""
                }

                dialog?.dismiss()

                mViewModel.callForgotPasswordApi().observe(viewLifecycleOwner, Observer {
                    if (it.responseCode == 200) {
                        commonCallbacks?.showAlertDialog(
                            it.successMsg,
                            DialogInterface.OnClickListener { _, _ ->
                                    goBack()
                            })
                    }

                })

            }

        }

    }

    var isNumber : Boolean? = false
    fun isValid(): Boolean {

        var email = mBinding.edtEmailOrPhone.get()

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

            else -> return true
        }
    }


    var dialog: androidx.appcompat.app.AlertDialog? = null

}
