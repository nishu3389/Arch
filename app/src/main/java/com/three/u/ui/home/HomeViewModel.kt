package com.three.u.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.model.request.RequestForgotPassword
import com.three.u.model.response.*
import com.three.u.util.Prefs
import com.three.u.util.Validator
import com.three.u.networking.Api

class HomeViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    val requestForgotPassword = ObservableField<RequestForgotPassword>()
    val responseForgotPassword = MutableLiveData<MasterResponse<ResponseLogin>>()

    var responseAdv : MutableLiveData<MasterResponse<AdvlistResponse>>? = null
    var responseAdvertsementStrip = ResponseAdvertsementPopup()

    val errEmail = ObservableField<String>()
    var checkListProgrss = ObservableField<Int>()


    init {
        checkListProgrss.set(Prefs.get().checkListPercent)
    }

    fun validateInput(): Boolean {

        val data = requestForgotPassword.get() ?: return false

        if (!Validator.isEmailValid(data.email, errEmail)) {
            return false
        }

        return true
    }

    fun callForgotPasswordApi() {
        baseRepo.restClient.callApi(
            Api.FORGOT_PASSWORD,
            requestForgotPassword.get(),
            responseForgotPassword
        )
    }



}