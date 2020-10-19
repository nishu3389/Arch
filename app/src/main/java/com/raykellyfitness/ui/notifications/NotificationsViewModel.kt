package com.raykellyfitness.ui.notifications

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.model.response.*
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.util.Validator
import com.raykellyfitness.networking.Api

class NotificationsViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

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