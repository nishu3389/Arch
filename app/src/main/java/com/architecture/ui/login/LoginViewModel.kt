package com.architecture.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.architecture.base.*
import com.architecture.model.request.RequestLogin
import com.architecture.model.request.RequestSocialLogin
import com.architecture.model.response.MasterResponse
import com.architecture.model.response.ResponseLogin
import com.architecture.util.Prefs
import com.architecture.networking.Api

class LoginViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    val errEmail = ObservableField<String>("")
    val errPassword = ObservableField<String>("")

    val requestLogin = ObservableField<RequestLogin>()
    var responseLogin = MutableLiveData<MasterResponse<ResponseLogin>>()

    val requestSocialLogin = ObservableField<RequestSocialLogin>()

    init {
        requestLogin.set(RequestLogin())
        requestSocialLogin.set(RequestSocialLogin())
    }


    fun callLoginApi(): MutableLiveData<MasterResponse<ResponseLogin>> {
        responseLogin = MutableLiveData<MasterResponse<ResponseLogin>>()
        Prefs.get().isFromSocialLogin = false
        baseRepo.restClient.callApi(Api.LOGIN, requestLogin.get()!!, responseLogin)
        return responseLogin
    }


}