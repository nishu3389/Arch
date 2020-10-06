package com.raykellyfitness.ui.webview

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.base.isEmptyy
import com.raykellyfitness.base.showWarning
import com.raykellyfitness.model.request.*
import com.raykellyfitness.model.response.MasterResponse

class WebviewViewModel(controller: AsyncViewController) : BaseViewModel(controller) {
    var responseAddBankAccount = MutableLiveData<MasterResponse<Any>>()
    val requestAddBankAccount = ObservableField<RequestAddBankAccount>()
    var responseBankSetupDetail = MutableLiveData<MasterResponse<ResponseGetBankSetupDetail>>()

    init {
        requestAddBankAccount.set(RequestAddBankAccount())
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



    /*  fun callUpdateCheckListAPI() : MutableLiveData<MasterResponse<Any>> {
          baseRepo.restClient.callApi(Api.NEW_UPDATE_CUSTOMER_CHECKLIST, requestAddChecklist.get(), responseAddCheckList)
          return responseAddCheckList
      }
  */

}