package com.raykellyfitness.ui.meal

import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.response.*
import com.raykellyfitness.networking.Api
import com.raykellyfitness.networking.Api.POST_TYPE_MEAL
import com.raykellyfitness.ui.tipsandtricks.RequestPosts

class MealPlanListViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var responseTipsOuter : MutableLiveData<MasterResponse<ResponseMealOuter>>? = null
    var requestPosts = RequestPosts(type = "")


    fun callgetPostsApi() : MutableLiveData<MasterResponse<ResponseMealOuter>> {
        responseTipsOuter = MutableLiveData<MasterResponse<ResponseMealOuter>>()
        baseRepo.restClient.callApi(Api.GET_POSTS_MEAL, requestPosts, responseTipsOuter!!)
        return responseTipsOuter!!
    }


}