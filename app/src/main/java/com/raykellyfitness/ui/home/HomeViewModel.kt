package com.raykellyfitness.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.model.request.RequestSavePayment
import com.raykellyfitness.model.response.*
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.util.Validator
import com.raykellyfitness.networking.Api

class HomeViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    val requestSavePayment = ObservableField<RequestSavePayment>()
    var responseSavePayment = MutableLiveData<MasterResponse<Boolean>>()

    fun callSavePaymentApi() : MutableLiveData<MasterResponse<Boolean>> {
        responseSavePayment = MutableLiveData<MasterResponse<Boolean>>()
        baseRepo.restClient.callApi(Api.SAVE_PAYMENT, requestSavePayment.get(), responseSavePayment)
        return responseSavePayment
    }


}