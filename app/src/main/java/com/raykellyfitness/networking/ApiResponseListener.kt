package com.raykellyfitness.networking

import com.raykellyfitness.model.response.MasterResponse

interface ApiResponseListener {
    fun onApiCallSuccess(apiUrl: String, body: MasterResponse<*>) : Boolean
    fun onApiCallFailed(apiUrl: String, errCode: Int, errorMessage: String) : Boolean
}