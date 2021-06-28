package com.architecture.networking

data class WrapperApiError (val apiName : String = "", val responseCode : Int = 0, val failureMsg : String = "", var validationErrors : List<String>? = emptyList<String>())

