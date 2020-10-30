package com.raykellyfitness.ui.health.bloodpressure.add_bloodpressure

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.base.isEmptyy
import com.raykellyfitness.base.showWarning
import com.raykellyfitness.model.request.*
import com.raykellyfitness.model.response.*
import com.raykellyfitness.networking.Api
import com.raykellyfitness.util.Constant.ENTER_DIASTOLIC_BP
import com.raykellyfitness.util.Constant.ENTER_DIASTOLIC_BP_RANGE
import com.raykellyfitness.util.Constant.ENTER_SYSTOLIC_BP
import com.raykellyfitness.util.Constant.ENTER_SYSTOLIC_BP_RANGE

class AddBloodPressureViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var requestAddBloodPressure = ObservableField<RequestAddBloodPressure>()
    var responseAddBloodPressure = MutableLiveData<MasterResponse<ResponseAddBloodPressure>>()

    init {
        requestAddBloodPressure.set(RequestAddBloodPressure())
    }

    fun validateInput(): Boolean {

        val data = requestAddBloodPressure.get() ?: return false

        if (data.blood_pressure_diastolic.isEmptyy() || data.blood_pressure_diastolic!!.toDouble() <= 0) {
            ENTER_DIASTOLIC_BP?.showWarning()
            return false
        }
        if (data.blood_pressure_diastolic!!.toDouble() < 1 || data.blood_pressure_diastolic!!.toDouble() > 250) {
            ENTER_DIASTOLIC_BP_RANGE?.showWarning()
            return false
        }

        if (data.blood_pressure_systolic.isEmptyy() || data.blood_pressure_systolic!!.toDouble() <= 0) {
            ENTER_SYSTOLIC_BP?.showWarning()
            return false
        }
        if (data.blood_pressure_systolic!!.toDouble() < 1 || data.blood_pressure_systolic!!.toDouble() > 250) {
            ENTER_SYSTOLIC_BP_RANGE?.showWarning()
            return false
        }


        return true
    }

    fun callAddBllodPressureApi(): MutableLiveData<MasterResponse<ResponseAddBloodPressure>> {
        responseAddBloodPressure = MutableLiveData<MasterResponse<ResponseAddBloodPressure>>()
        baseRepo.restClient.callApi(Api.ADD_BLOOD_PRESSURE,
                                    requestAddBloodPressure.get(),
                                    responseAddBloodPressure)
        return responseAddBloodPressure
    }


}