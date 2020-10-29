package com.raykellyfitness.ui.tipsandtricks.tips_detail

import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.response.*
import com.raykellyfitness.networking.Api
import com.raykellyfitness.ui.tipsandtricks.RequestTipsDetail
import com.raykellyfitness.ui.tipsandtricks.ResponseTipsDetail
import com.raykellyfitness.util.Constant

class TipsDetailViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var type : String = ""
    var model : ResponseTipsDetail? = null

    var responseTipsDetail : MutableLiveData<MasterResponse<ResponseTipsDetail>>? = null


    fun callTipsDetailApi(id: String, type: String) : MutableLiveData<MasterResponse<ResponseTipsDetail>> {

        var apiName = ""
        when(type.toLowerCase()){
            Constant.POST_TYPE_TIPS.toLowerCase() -> apiName = Api.API_POST_TYPE_TIPS
            Constant.POST_TYPE_MEAL.toLowerCase() -> apiName = Api.API_POST_TYPE_MEAL
            Constant.POST_TYPE_EXERCISE.toLowerCase() -> apiName = Api.API_POST_TYPE_EXERCISE
            Constant.POST_TYPE_MOTIVATION.toLowerCase() -> apiName = Api.API_POST_TYPE_MOTIVATION
            Constant.POST_TYPE_BLOG.toLowerCase() -> apiName = Api.API_POST_TYPE_BLOG
        }

        var requestPosts = RequestTipsDetail(id = id, type = apiName)
        responseTipsDetail = MutableLiveData<MasterResponse<ResponseTipsDetail>>()
        baseRepo.restClient.callApi(Api.POST_DETAIL, requestPosts, responseTipsDetail!!)
        return responseTipsDetail!!
    }


}
