package com.architecture.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.architecture.base.AsyncViewController
import com.architecture.base.BaseViewModel
import com.architecture.model.request.RequestSavePayment
import com.architecture.model.response.*
import com.architecture.networking.Api

class HomeViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    val requestSavePayment = ObservableField<RequestSavePayment>()
    var responseSavePayment = MutableLiveData<MasterResponse<Boolean>>()

    fun callSavePaymentApi() : MutableLiveData<MasterResponse<Boolean>> {
        responseSavePayment = MutableLiveData<MasterResponse<Boolean>>()
        baseRepo.restClient.callApi(Api.SAVE_PAYMENT, requestSavePayment.get(), responseSavePayment)
        return responseSavePayment
    }


}