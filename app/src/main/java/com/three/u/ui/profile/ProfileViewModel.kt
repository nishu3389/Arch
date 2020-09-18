package com.three.u.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.model.request.RequestEnableNotification
import com.three.u.model.request.RequestGetUserProfile
import com.three.u.model.request.ResponseCountryStateCity
import com.three.u.model.response.MasterResponse
import com.three.u.model.response.ResponseGetProfile
import com.three.u.networking.Api
import com.three.u.util.Prefs

class ProfileViewModel(controller: AsyncViewController) :
    BaseViewModel(controller) {


    val user = Prefs.get().loginData
    val requestGetUserProfile = ObservableField<RequestGetUserProfile>()
    val requestEnableNotification = ObservableField<RequestEnableNotification>()
    val responseGetUserProfile = MutableLiveData<MasterResponse<ResponseGetProfile>>()


    init {
        requestEnableNotification.set(RequestEnableNotification())
    }

    fun callGetUserProfileApi(): MutableLiveData<MasterResponse<ResponseGetProfile>> {
        baseRepo.restClient.callApi(
            Api.GETCUSTOMERPROFILE, requestGetUserProfile.get(),
            responseGetUserProfile
        )

        return responseGetUserProfile
    }





}