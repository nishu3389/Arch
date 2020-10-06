package com.raykellyfitness.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.request.RequestEnableNotification
import com.raykellyfitness.model.request.RequestGetUserProfile
import com.raykellyfitness.model.response.MasterResponse
import com.raykellyfitness.model.response.ResponseGetProfile
import com.raykellyfitness.networking.Api
import com.raykellyfitness.util.Prefs

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