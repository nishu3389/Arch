package com.three.u.login

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.three.u.R
import com.three.u.base.*
import com.three.u.model.request.RequestLogin
import com.three.u.model.request.RequestSocialLogin
import com.three.u.model.response.MasterResponse
import com.three.u.model.response.ResponseLogin
import com.three.u.util.Prefs
import com.three.u.util.Validator
import com.three.u.webservice.Api
import org.json.JSONObject

class LoginViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    val errEmail = ObservableField<String>("")
    val errPassword = ObservableField<String>("")

    val requestLogin = ObservableField<RequestLogin>()
    val responseLogin = MutableLiveData<MasterResponse<ResponseLogin>>()

    val requestSocialLogin = ObservableField<RequestSocialLogin>()

    init {
        requestLogin.set(RequestLogin())
        requestSocialLogin.set(RequestSocialLogin())
    }



    fun callLoginApi() {
            Prefs.get().isFromSocialLogin = false
            baseRepo.restClient.callApi(Api.LOGIN, requestLogin.get()!!, responseLogin)
    }


}