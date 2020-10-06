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
import com.three.u.networking.Api.POST_TYPE_MEAL
import com.three.u.ui.tipsandtricks.RequestPosts
import com.three.u.ui.tipsandtricks.ResponseTipsOuter

class MealPlanListViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var responseTipsOuter : MutableLiveData<MasterResponse<ResponseMealOuter>>? = null
    var requestPosts = RequestPosts(type = POST_TYPE_MEAL)


    fun callgetPostsApi() : MutableLiveData<MasterResponse<ResponseMealOuter>> {
        responseTipsOuter = MutableLiveData<MasterResponse<ResponseMealOuter>>()
        baseRepo.restClient.callApi(Api.GET_POSTS_MEAL, requestPosts, responseTipsOuter!!)
        return responseTipsOuter!!
    }


}