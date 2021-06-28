package com.architecture.networking

import com.architecture.model.response.MasterResponse

interface ApiResponseListener {
    fun onApiCallSuccess(apiUrl: String, body: MasterResponse<*>) : Boolean
    fun onApiCallFailed(apiUrl: String, errCode: Int, errorMessage: String) : Boolean
}