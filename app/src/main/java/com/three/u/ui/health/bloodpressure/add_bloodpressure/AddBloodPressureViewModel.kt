package com.three.u.ui.health.bloodpressure.add_bloodpressure

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

class AddBloodPressureViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var requestAddBloodPressure = ObservableField<RequestAddBloodPressure>()
    var responseAddBloodPressure = MutableLiveData<MasterResponse<ResponseAddBloodPressure>>()

    init {
        requestAddBloodPressure.set(RequestAddBloodPressure())
    }

    fun validateInput(): Boolean {

        val data = requestAddBloodPressure.get() ?: return false

        if (data.blood_pressure_diastolic.isEmptyy() || data.blood_pressure_diastolic!!.toDouble()<=0) {
            "Please enter your diastolic blood pressure".showWarning()
            return false
        }

        if (data.blood_pressure_systolic.isEmptyy() || data.blood_pressure_systolic!!.toDouble()<=0) {
            "Please enter your systolic blood pressure".showWarning()
            return false
        }


        return true
    }

    fun callAddBllodPressureApi() : MutableLiveData<MasterResponse<ResponseAddBloodPressure>> {
        responseAddBloodPressure = MutableLiveData<MasterResponse<ResponseAddBloodPressure>>()
        baseRepo.restClient.callApi(Api.ADD_BLOOD_PRESSURE, requestAddBloodPressure.get(), responseAddBloodPressure)
        return responseAddBloodPressure
    }



}