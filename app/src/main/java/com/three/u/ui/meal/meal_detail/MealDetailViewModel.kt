package com.three.u.ui.meal.meal_detail

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
import com.three.u.ui.meal.ResponseMealInner
import com.three.u.ui.tipsandtricks.RequestTipsDetail
import com.three.u.ui.tipsandtricks.ResponseTipsDetail

class MealDetailViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var model : ResponseTipsDetail? = null

    var responseTipsDetail : MutableLiveData<MasterResponse<ResponseTipsDetail>>? = null


    fun callTipsDetailApi(id : String) : MutableLiveData<MasterResponse<ResponseTipsDetail>> {
        var requestPosts = RequestTipsDetail(id = id, type = "meal")
        responseTipsDetail = MutableLiveData<MasterResponse<ResponseTipsDetail>>()
        baseRepo.restClient.callApi(Api.POST_DETAIL, requestPosts, responseTipsDetail!!)
        return responseTipsDetail!!
    }




}