package com.raykellyfitness.ui.health.weight.add_weight

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.base.isEmptyy
import com.raykellyfitness.base.showWarning
import com.raykellyfitness.model.request.*
import com.raykellyfitness.model.response.*
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.networking.Api
import com.raykellyfitness.util.Constant.ENTER_HEIGHT
import com.raykellyfitness.util.Constant.ENTER_HEIGHT_RANGE
import com.raykellyfitness.util.Constant.ENTER_WEIGHT
import com.raykellyfitness.util.Constant.ENTER_WEIGHT_RANGE

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
        if (data.weight!!.toDouble()<1 || data.weight!!.toDouble()>500) {
            ENTER_WEIGHT_RANGE?.showWarning()
            return false
        }

        if (data.height.isEmptyy() || data.height!!.toDouble()<=0) {
            ENTER_HEIGHT?.showWarning()
            return false
        }
        if (data.height!!.toDouble()<1 || data.height!!.toDouble()>250) {
            ENTER_HEIGHT_RANGE?.showWarning()
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