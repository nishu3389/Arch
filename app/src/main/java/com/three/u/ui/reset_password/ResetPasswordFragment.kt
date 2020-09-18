package com.three.u.ui.reset_password

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.three.u.R
import com.three.u.base.*
import com.three.u.databinding.FragmentResetPasswordBinding
import com.three.u.model.request.RequestResetPassword
import com.three.u.ui.activity.AccountHandlerActivity
import com.three.u.ui.activity.HomeActivity

class ResetPasswordFragment : BaseFragment() {

    lateinit var mViewModel: ResetPasswordViewModel
    lateinit var mBinding: FragmentResetPasswordBinding

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
        mBinding = FragmentResetPasswordBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.imgBack.push()?.setOnClickListener {
            back()
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(ResetPasswordViewModel::class.java)
        mViewModel.request.set(RequestResetPassword())
    }

    private fun setupObserver() {
        mViewModel.responseForgotPassword.observe(this, Observer {
           if (it.responseCode == 200) {
                commonCallbacks?.showAlertDialog(
                    it.message,
                    DialogInterface.OnClickListener { _, _ ->
                        findNavController().popBackStack(R.id.LoginFragment,false)
                    })
            }
        })
    }

    fun back(){
        findNavController().popBackStack(R.id.LoginFragment,false)
    }

    inner class ClickHandler {

        fun onClickSubmit() {
            commonCallbacks?.hideKeyboard()
            if (mViewModel.validateInput()) {
                mViewModel.callResetPasswordApi()
            }
        }

        fun back(){
            findNavController().popBackStack(R.id.ForgotPasswordFragment,false)
        }

    }


}
