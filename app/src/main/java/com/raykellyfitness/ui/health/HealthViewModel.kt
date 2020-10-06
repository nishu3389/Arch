package com.raykellyfitness.ui.health

import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.request.ResponseAddBloodPressure
import com.raykellyfitness.model.request.ResponseAddBloodSugar
import com.raykellyfitness.model.request.ResponseAddWeight
import com.raykellyfitness.model.response.*
import com.raykellyfitness.networking.Api

class HealthViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var responseAddWeight : MutableLiveData<MasterResponse<ResponseAddWeight>>? = null
    var responseAddBloodSugar : MutableLiveData<MasterResponse<ResponseAddBloodSugar>>? = null
    var responseAddBloodPressure : MutableLiveData<MasterResponse<ResponseAddBloodPressure>>? = null


    fun callWeightListApi() : MutableLiveData<MasterResponse<ResponseAddWeight>> {
        responseAddWeight = MutableLiveData<MasterResponse<ResponseAddWeight>>()
        baseRepo.restClient.callApi(Api.LIST_WEIGHT, null, responseAddWeight!!)
        return responseAddWeight!!
    }

    fun callBloodSugarListApi() : MutableLiveData<MasterResponse<ResponseAddBloodSugar>> {
        responseAddBloodSugar = MutableLiveData<MasterResponse<ResponseAddBloodSugar>>()
        baseRepo.restClient.callApi(Api.LIST_BLOOD_SUGAR, null, responseAddBloodSugar!!)
        return responseAddBloodSugar!!
    }

    fun callBloodPressureListApi() : MutableLiveData<MasterResponse<ResponseAddBloodPressure>> {
        responseAddBloodPressure = MutableLiveData<MasterResponse<ResponseAddBloodPressure>>()
        baseRepo.restClient.callApi(Api.LIST_BLOOD_PRESSURE, null, responseAddBloodPressure!!)
        return responseAddBloodPressure!!
    }



}