package com.three.u.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.three.u.base.*
import com.three.u.model.request.RequestLogin
import com.three.u.model.request.RequestSocialLogin
import com.three.u.model.response.MasterResponse
import com.three.u.model.response.ResponseLogin
import com.three.u.util.Prefs
import com.three.u.networking.Api

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