package com.three.u.ui.tipsandtricks.tips_detail

import androidx.lifecycle.MutableLiveData
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.model.response.*
import com.three.u.networking.Api
import com.three.u.ui.tipsandtricks.RequestTipsDetail
import com.three.u.ui.tipsandtricks.ResponseTipsDetail

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
