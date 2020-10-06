package com.three.u.ui.health.weight.add_weight

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.base.isEmptyy
import com.three.u.base.showWarning
import com.three.u.model.request.*
import com.three.u.model.response.*
import com.three.u.util.Prefs
import com.three.u.util.Validator
import com.three.u.networking.Api
import com.three.u.util.Constant.ENTER_HEIGHT
import com.three.u.util.Constant.ENTER_WEIGHT

class AddWeightViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var requestAddWeight = ObservableField<RequestAddWeight>()
    var responseAddWeight = MutableLiveData<MasterResponse<ResponseAddWeight>>()

    init {
        requestAddWeight.set(RequestAddWeight())
    }

    fun validateInput(): Boolean {

        val data = requestAddWeight.get() ?: return false

        if (data.weight.isEmptyy() || data.weight!!.toDouble()<=0) {
            ENTER_WEIGHT?.showWarning()
            return false
        }

        if (data.height.isEmptyy() || data.height!!.toDouble()<=0) {
            ENTER_HEIGHT?.showWarning()
            return false
        }

        val user = Prefs.get().loginData
        user?.height = data.height!!

        Prefs.get().loginData = user

        return true
    }

    fun callAddWeightApi() : MutableLiveData<MasterResponse<ResponseAddWeight>> {
        responseAddWeight = MutableLiveData<MasterResponse<ResponseAddWeight>>()
        baseRepo.restClient.callApi(Api.ADD_WEIGHT, requestAddWeight.get(), responseAddWeight)
        return responseAddWeight
    }



}