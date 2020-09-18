package com.three.u.ui.forgot_password
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.model.request.RequestForgotPassword
import com.three.u.model.request.RequestVerifyOTP
import com.three.u.model.response.MasterResponse
import com.three.u.model.response.ResponseLogin
import com.three.u.networking.Api

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