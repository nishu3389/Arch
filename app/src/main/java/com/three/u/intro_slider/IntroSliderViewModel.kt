package com.three.u.intro_slider

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.model.request.RequestChangePassword
import com.three.u.model.response.MasterResponse
import com.three.u.model.response.ResponseChangePassword
import com.three.u.util.Validator
import com.three.u.webservice.Api


class IntroSliderViewModel(controller : AsyncViewController) : BaseViewModel(controller){

    val requestChangePassword = ObservableField<RequestChangePassword>(RequestChangePassword())
    val responseChangePassword = MutableLiveData<MasterResponse<ResponseChangePassword>>()

    val errOldPassword = ObservableField<String>()
    val errNewPassword = ObservableField<String>()
    val errConfirmPassword = ObservableField<String>()

    private fun callChangePasswordApi(){
        baseRepo.restClient.callApi(Api.ChangePassword, requestChangePassword.get(), responseChangePassword)
    }

    fun requestChangePassword() {
        if (Validator.validateChangePasswordForm(requestChangePassword.get()!!, errOldPassword, errNewPassword, errConfirmPassword)){
            controller?.hideKeyboard()
            callChangePasswordApi()
        }
    }

}