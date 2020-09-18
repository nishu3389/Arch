package com.three.u.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.three.u.model.response.MasterResponse
import com.three.u.networking.Api


class BaseActivityViewModel(viewController : AsyncViewController) : BaseViewModel(viewController){

    val progressDialogStatus : MutableLiveData<String> = MutableLiveData()
    val alertDialogController : MutableLiveData<String> = MutableLiveData()
    val keyboardController : MutableLiveData<Boolean> = MutableLiveData()
    var alertDialogSpecs : AlertDialogSpecs = AlertDialogSpecs()
    var responseLogOut : MutableLiveData<MasterResponse<Boolean>>? = null
    val responseOTP = MutableLiveData<MasterResponse<Boolean>>()

    var parcels : HashMap<Int, Bundle> = HashMap()

    fun logOut() : MutableLiveData<MasterResponse<Boolean>>{
        responseLogOut = MutableLiveData<MasterResponse<Boolean>>()
        baseRepo.restClient.callApi(Api.LOGOUT, null, responseLogOut!!)
        return responseLogOut!!
    }
}