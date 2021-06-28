package com.architecture.ui.webview

import androidx.lifecycle.MutableLiveData
import com.architecture.base.AsyncViewController
import com.architecture.base.BaseViewModel
import com.architecture.model.response.MasterResponse

class WebviewViewModel(controller: AsyncViewController) : BaseViewModel(controller) {
    var response = MutableLiveData<MasterResponse<Any>>()
}