package com.raykellyfitness.ui.tipsandtricks

import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.response.*
import com.raykellyfitness.networking.Api

class TipsAndTricksViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var responseTipsOuter : MutableLiveData<MasterResponse<ResponseTipsOuter>>? = null


    fun callgetPostsApi(type : String) : MutableLiveData<MasterResponse<ResponseTipsOuter>> {
        var requestPosts = RequestPosts(type = type)
        responseTipsOuter = MutableLiveData<MasterResponse<ResponseTipsOuter>>()
        baseRepo.restClient.callApi(Api.GET_POSTS_TIPS, requestPosts, responseTipsOuter!!)
        return responseTipsOuter!!
    }

}