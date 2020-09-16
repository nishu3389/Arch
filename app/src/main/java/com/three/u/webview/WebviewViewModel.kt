package com.three.u.webview

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.three.u.R
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.base.isEmptyy
import com.three.u.base.showWarning
import com.three.u.model.request.*
import com.three.u.model.response.AddCheckListResponse
import com.three.u.model.response.MasterChecklistItems
import com.three.u.model.response.MasterResponse
import com.three.u.webservice.Api

class WebviewViewModel(controller: AsyncViewController) : BaseViewModel(controller) {
    var responseAddBankAccount = MutableLiveData<MasterResponse<Any>>()
    val requestAddBankAccount = ObservableField<RequestAddBankAccount>()
    var responseBankSetupDetail = MutableLiveData<MasterResponse<ResponseGetBankSetupDetail>>()

    init {
        requestAddBankAccount.set(RequestAddBankAccount())
    }

    fun callGetBankSetupDetailApi() : MutableLiveData<MasterResponse<ResponseGetBankSetupDetail>>{
        responseBankSetupDetail = MutableLiveData<MasterResponse<ResponseGetBankSetupDetail>>()
        baseRepo.restClient.callApi(Api.GET_BANK_SETUP_DETAIL, null, responseBankSetupDetail)
        return responseBankSetupDetail
    }

    fun isValid(): Boolean {
        val req = requestAddBankAccount.get()
        if (req?.name.isEmptyy()) {
            "Please enter Account Holder Name".showWarning()
            return false
        } else if (req?.country.isEmptyy()) {
            "Please select country".showWarning()
            return false
        } else if (req?.currency.isEmptyy()) {
            "Please select currency".showWarning()
            return false
        } else if (req?.bankAccountNumber.isEmptyy()) {
            "Please enter Bank Account Number".showWarning()
            return false
        } else if (req?.bankAccountNumber!!.length < 9 || req?.bankAccountNumber!!.length > 18) {
            "Bank Account Number length should be between 9 to 18 digits".showWarning()
            return false
        } else if (req?.routingNumber.isEmptyy()) {
            "Please enter Routing Number".showWarning()
            return false
        } else if (req?.routingNumber!!.length < 6 || req?.routingNumber!!.length > 9) {
            "Routing Number length should be 8 or 9 digits".showWarning()
            return false
        }
        return true
    }

    fun callAddBankAccountAPI(): MutableLiveData<MasterResponse<Any>> {
        responseAddBankAccount = MutableLiveData<MasterResponse<Any>>()
        baseRepo.restClient.callApi(
            Api.ADD_BANK_ACCOUNT,
            requestAddBankAccount.get(),
            responseAddBankAccount
        )
        return responseAddBankAccount
    }

    /*  fun callUpdateCheckListAPI() : MutableLiveData<MasterResponse<Any>> {
          baseRepo.restClient.callApi(Api.NEW_UPDATE_CUSTOMER_CHECKLIST, requestAddChecklist.get(), responseAddCheckList)
          return responseAddCheckList
      }
  */

}