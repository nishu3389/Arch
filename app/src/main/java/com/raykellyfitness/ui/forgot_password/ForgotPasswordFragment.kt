package com.raykellyfitness.ui.forgot_password

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentForgotPasswordBinding
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.util.Validator

class ForgotPasswordFragment : BaseFragment() {

    lateinit var mViewModel: ForgotPasswordViewModel
    lateinit var mBinding: FragmentForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupObserver()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentForgotPasswordBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(ForgotPasswordViewModel::class.java)
        mViewModel.requestForgotPassword.set(RequestForgotPassword())
    }

    private fun setupObserver() {

    }

    inner class ClickHandler {

        fun onClickSubmit() {
            commonCallbacks?.hideKeyboard()

            if (isValid()) {
                mViewModel.callForgotPasswordApi().observe(viewLifecycleOwner, Observer {
                    if (it.responseCode == 200)
                        navigate(R.id.ResetPasswordFragment)
                })
            }

        }

        fun back(){
            findNavController().popBackStack(R.id.LoginFragment,false)
        }

    }

    fun isValid(): Boolean {

        var email = mBinding.edtEmailOrPhone.get()

        return when {
            TextUtils.isEmpty(email) -> {
                mViewModel.errEmail.set(MainApplication.get().getString(R.string.err_email_missing))
                mViewModel.errEmail.get()?.showWarning()
                false
            }

            !Validator.isEmailValid(email, mViewModel.errEmail) -> {
                false
            }

            else -> true
        }
    }

}
