package com.three.u.ui.changepassword

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.three.u.R
import com.three.u.ui.activity.HomeActivity
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseFragment
import com.three.u.base.MyViewModelProvider
import com.three.u.databinding.FragmentChangePasswordBinding
import com.three.u.model.request.RequestChangePassword


class ChangePasswordFragment : BaseFragment() {

    lateinit var mViewModel: ChangePasswordViewModel
    lateinit var mBinding: FragmentChangePasswordBinding

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
        mBinding = FragmentChangePasswordBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
            viewModel = mViewModel
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).setTitle(resources.getString(R.string.change_password))
    }


    private fun setupViewModel() {
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(ChangePasswordViewModel::class.java)
        mViewModel.requestChangePassword.set(RequestChangePassword())
    }

    private fun setupObserver() {
        mViewModel.responseChangePassword.observe(this, Observer {
            if (it.data != null) {
                commonCallbacks?.showAlertDialog(
                    it.successMsg,
                    DialogInterface.OnClickListener { _, _ ->
                        activity?.onBackPressed()
                    })
            }
        })
    }

    override fun onApiRequestFailed(apiUrl: String, errCode: Int, errorMessage: String): Boolean {
        commonCallbacks?.showAlertDialog(
            errorMessage,
            DialogInterface.OnClickListener { _, _ ->
//                activity?.onBackPressed()
            })
        return super.onApiRequestFailed(apiUrl, errCode, errorMessage)
    }

    inner class ClickHandler {

        fun onClickSubmit() {
            mViewModel.requestChangePassword()
        }
    }


}
