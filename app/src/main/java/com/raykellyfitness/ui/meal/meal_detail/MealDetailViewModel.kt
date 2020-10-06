package com.raykellyfitness.ui.meal.meal_detail

import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.response.*
import com.raykellyfitness.networking.Api
import com.raykellyfitness.ui.tipsandtricks.RequestTipsDetail
import com.raykellyfitness.ui.tipsandtricks.ResponseTipsDetail

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