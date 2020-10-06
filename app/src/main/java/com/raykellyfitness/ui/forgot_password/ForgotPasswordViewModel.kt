package com.raykellyfitness.ui.forgot_password
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.model.request.RequestVerifyOTP
import com.raykellyfitness.model.response.MasterResponse
import com.raykellyfitness.model.response.ResponseLogin
import com.raykellyfitness.networking.Api

class ForgotPasswordViewModel(controller: AsyncViewController) : BaseViewModel(controller) {
    val requestForgotPassword = ObservableField<RequestForgotPassword>()
    var responseForgotPassword = MutableLiveData<MasterResponse<ResponseLogin>>()

    val requestOTP = ObservableField<RequestVerifyOTP>()
    var responseOTP = MutableLiveData<MasterResponse<Boolean>>()

    val errEmail = ObservableField<String>()
    val errNewPassword = ObservableField<String>()
    val errConfirmPassword = ObservableField<String>()

    init {
        requestOTP.set(RequestVerifyOTP())
    }

    fun callForgotPasswordApi() : MutableLiveData<MasterResponse<ResponseLogin>>{
        responseForgotPassword = MutableLiveData<MasterResponse<ResponseLogin>>()
        baseRepo.restClient.callApi(Api.FORGOT_PASSWORD, requestForgotPassword.get(), responseForgotPassword)
        return responseForgotPassword
    }

    fun callResetPasswordApi() : MutableLiveData<MasterResponse<Boolean>> {
        responseOTP = MutableLiveData<MasterResponse<Boolean>>()
        baseRepo.restClient.callApi(Api.RESET_PASSWORD, requestOTP.get(), responseOTP)
        return responseOTP
    }

}