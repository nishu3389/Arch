package com.three.u.ui.meal

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.model.request.RequestForgotPassword
import com.three.u.model.request.ResponseAddBloodPressure
import com.three.u.model.request.ResponseAddBloodSugar
import com.three.u.model.request.ResponseAddWeight
import com.three.u.model.response.*
import com.three.u.util.Prefs
import com.three.u.util.Validator
import com.three.u.networking.Api

class MealPlanViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

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