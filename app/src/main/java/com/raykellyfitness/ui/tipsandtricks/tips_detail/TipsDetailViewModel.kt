package com.raykellyfitness.ui.tipsandtricks.tips_detail

import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.response.*
import com.raykellyfitness.networking.Api
import com.raykellyfitness.ui.tipsandtricks.RequestTipsDetail
import com.raykellyfitness.ui.tipsandtricks.ResponseTipsDetail

class TipsDetailViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    var type : String = ""
    var model : ResponseTipsDetail? = null

    var responseTipsDetail : MutableLiveData<MasterResponse<ResponseTipsDetail>>? = null


    fun callTipsDetailApi(id: String, type: String) : MutableLiveData<MasterResponse<ResponseTipsDetail>> {
        var requestPosts = RequestTipsDetail(id = id, type = type)
        responseTipsDetail = MutableLiveData<MasterResponse<ResponseTipsDetail>>()
        baseRepo.restClient.callApi(Api.POST_DETAIL, requestPosts, responseTipsDetail!!)
        return responseTipsDetail!!
    }


}
