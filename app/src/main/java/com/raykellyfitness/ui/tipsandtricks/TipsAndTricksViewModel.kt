package com.raykellyfitness.ui.tipsandtricks

import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.response.*
import com.raykellyfitness.networking.Api
import com.raykellyfitness.networking.Api.API_POST_TYPE_BLOG
import com.raykellyfitness.networking.Api.API_POST_TYPE_EXERCISE
import com.raykellyfitness.networking.Api.API_POST_TYPE_MEAL
import com.raykellyfitness.networking.Api.API_POST_TYPE_MOTIVATION
import com.raykellyfitness.networking.Api.API_POST_TYPE_TIPS
import com.raykellyfitness.util.Constant.POST_TYPE_BLOG
import com.raykellyfitness.util.Constant.POST_TYPE_EXERCISE
import com.raykellyfitness.util.Constant.POST_TYPE_MEAL
import com.raykellyfitness.util.Constant.POST_TYPE_MOTIVATION
import com.raykellyfitness.util.Constant.POST_TYPE_TIPS

class TipsAndTricksViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var responseTipsOuter : MutableLiveData<MasterResponse<ResponseTipsOuter>>? = null


    fun callgetPostsApi(type : String) : MutableLiveData<MasterResponse<ResponseTipsOuter>> {

        var apiName = ""
        when(type.toLowerCase()){
            POST_TYPE_TIPS.toLowerCase() -> apiName = API_POST_TYPE_TIPS
            POST_TYPE_MEAL.toLowerCase() -> apiName = API_POST_TYPE_MEAL
            POST_TYPE_EXERCISE.toLowerCase() -> apiName = API_POST_TYPE_EXERCISE
            POST_TYPE_MOTIVATION.toLowerCase() -> apiName = API_POST_TYPE_MOTIVATION
            POST_TYPE_BLOG.toLowerCase() -> apiName = API_POST_TYPE_BLOG
        }

        var requestPosts = RequestPosts(type = apiName)
        responseTipsOuter = MutableLiveData<MasterResponse<ResponseTipsOuter>>()
        baseRepo.restClient.callApi(Api.GET_POSTS_TIPS, requestPosts, responseTipsOuter!!)
        return responseTipsOuter!!
    }

}