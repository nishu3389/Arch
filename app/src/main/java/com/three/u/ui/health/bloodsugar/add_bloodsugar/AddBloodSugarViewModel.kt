package com.three.u.ui.health.bloodsugar.add_bloodsugar

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
import com.three.u.util.Constant.ENTER_FASTING_SUGAR
import com.three.u.util.Constant.ENTER_POST_FASTING_SUGAR
import com.three.u.util.Constant.ENTER_WEIGHT

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