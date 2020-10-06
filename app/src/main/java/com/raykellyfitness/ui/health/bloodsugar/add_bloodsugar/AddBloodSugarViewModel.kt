package com.raykellyfitness.ui.health.bloodsugar.add_bloodsugar

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.base.isEmptyy
import com.raykellyfitness.base.showWarning
import com.raykellyfitness.model.request.*
import com.raykellyfitness.model.response.*
import com.raykellyfitness.networking.Api
import com.raykellyfitness.util.Constant.ENTER_FASTING_SUGAR
import com.raykellyfitness.util.Constant.ENTER_POST_FASTING_SUGAR

class AddBloodSugarViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var requestAddBloodSugar = ObservableField<RequestAddBloodSugar>()
    var responseAddBloodSugar = MutableLiveData<MasterResponse<ResponseAddBloodSugar>>()

    init {
        requestAddBloodSugar.set(RequestAddBloodSugar())
    }

    fun validateInput(): Boolean {

        val data = requestAddBloodSugar.get() ?: return false

        if (data.blood_sugar_fasting.isEmptyy() || data.blood_sugar_fasting!!.toDouble()<=0) {
            ENTER_FASTING_SUGAR?.showWarning()
            return false
        }

        if (data.blood_sugar_postprandial.isEmptyy() || data.blood_sugar_postprandial!!.toDouble()<=0) {
            ENTER_POST_FASTING_SUGAR?.showWarning()
            return false
        }

        return true
    }

    fun callAddBloodSugarApi() : MutableLiveData<MasterResponse<ResponseAddBloodSugar>> {
        responseAddBloodSugar = MutableLiveData<MasterResponse<ResponseAddBloodSugar>>()
        baseRepo.restClient.callApi(Api.ADD_BLOOD_SUGAR, requestAddBloodSugar.get(), responseAddBloodSugar)
        return responseAddBloodSugar
    }



}