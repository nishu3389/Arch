package com.raykellyfitness.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.*
import com.raykellyfitness.model.request.RequestLogin
import com.raykellyfitness.model.request.RequestSocialLogin
import com.raykellyfitness.model.response.MasterResponse
import com.raykellyfitness.model.response.ResponseLogin
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.networking.Api

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