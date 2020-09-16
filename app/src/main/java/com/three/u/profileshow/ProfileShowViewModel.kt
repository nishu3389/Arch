package com.three.u.profileshow

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.three.u.base.AsyncViewController
import com.three.u.base.BaseViewModel
import com.three.u.model.request.RequestEnableNotification
import com.three.u.model.request.RequestGetUserProfile
import com.three.u.model.request.ResponseCountryStateCity
import com.three.u.model.response.MasterResponse
import com.three.u.model.response.ResponseGetProfile
import com.three.u.util.Prefs
import com.three.u.webservice.Api

class ProfileShowViewModel(controller: AsyncViewController) :
    BaseViewModel(controller) {


    /*-------------GET COUNTRY/STATE/CITY DATA------------------------*/
    val requestGetCountryStateCity = ObservableField<RequestGetUserProfile>()
    var responseCountryStateCity = MutableLiveData<MasterResponse<ResponseCountryStateCity>>()

    val requestGetUserProfile = ObservableField<RequestGetUserProfile>()
    val requestEnableNotification = ObservableField<RequestEnableNotification>()
    var responseEnableNotification = MutableLiveData<MasterResponse<Boolean>>()
    val responseGetUserProfile = MutableLiveData<MasterResponse<ResponseGetProfile>>()

    val errName = ObservableField<String>("")
    val errContact = ObservableField<String>("")
    val errAddress = ObservableField<String>("")
    val errAge = ObservableField<String>("")
    val errGender = ObservableField<String>("")

    init {
        requestGetUserProfile.set(RequestGetUserProfile(Prefs.get().loginData?.id!!))
        requestGetCountryStateCity.set(RequestGetUserProfile(Prefs.get().loginData?.id!!))
        requestEnableNotification.set(RequestEnableNotification())
    }

    fun callGetUserProfileApi(): MutableLiveData<MasterResponse<ResponseGetProfile>> {
        baseRepo.restClient.callApi(
            Api.GETCUSTOMERPROFILE, requestGetUserProfile.get(),
            responseGetUserProfile
        )

        return responseGetUserProfile
    }

    fun callEnableNotification(): MutableLiveData<MasterResponse<Boolean>> {
        responseEnableNotification = MutableLiveData<MasterResponse<Boolean>>()
        baseRepo.restClient.callApi(Api.ENABLE_NOTIFICATION, requestEnableNotification.get(), responseEnableNotification)
        return responseEnableNotification
    }

    fun callGetCountryStateCityApi(): MutableLiveData<MasterResponse<ResponseCountryStateCity>> {
        responseCountryStateCity = MutableLiveData<MasterResponse<ResponseCountryStateCity>>()
        baseRepo.restClient.callApi(
            Api.CountryStateCityLookup,
            requestGetCountryStateCity.get(),
            responseCountryStateCity
        )
        return responseCountryStateCity
    }

}