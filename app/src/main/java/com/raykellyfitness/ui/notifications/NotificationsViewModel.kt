package com.raykellyfitness.ui.notifications

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseViewModel
import com.raykellyfitness.model.request.RequestForgotPassword
import com.raykellyfitness.model.request.RequestNotifications
import com.raykellyfitness.model.request.ResponseNotifications
import com.raykellyfitness.model.response.*
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.util.Validator
import com.raykellyfitness.networking.Api
import com.raykellyfitness.networking.Api.Notifications

class NotificationsViewModel(controller: AsyncViewController) : BaseViewModel(controller) {

    private val requestNotifications = ObservableField<RequestNotifications>()
    private var responseNotifications = MutableLiveData<MasterResponse<ResponseNotifications>>()

    init {
        requestNotifications.set(RequestNotifications(Notifications, Notifications))
    }

    fun callNotificationsApi() : MutableLiveData<MasterResponse<ResponseNotifications>> {
        responseNotifications = MutableLiveData<MasterResponse<ResponseNotifications>>()
        baseRepo.restClient.callApi(Notifications, requestNotifications.get(), responseNotifications)
        return responseNotifications
    }

}