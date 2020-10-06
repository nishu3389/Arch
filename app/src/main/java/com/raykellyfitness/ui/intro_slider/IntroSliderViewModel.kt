package com.raykellyfitness.ui.intro_slider

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.request.RequestChangePassword
import com.raykellyfitness.model.response.MasterResponse
import com.raykellyfitness.model.response.ResponseChangePassword
import com.raykellyfitness.util.Validator
import com.raykellyfitness.networking.Api


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