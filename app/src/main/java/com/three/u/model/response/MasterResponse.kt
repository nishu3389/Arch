package com.three.u.model.response

import com.google.gson.annotations.SerializedName

data class MasterResponse<T>(

	@field:SerializedName("data")
	var data: T? = null,

	@field:SerializedName("status")
	var status: Boolean? = false,

	@field:SerializedName("statusCode")
	var responseCode: Int = 0,

	@field:SerializedName("message")
	var message: String = "",

	@field:SerializedName("apiName")
	var apiName: String = "",

	@field:SerializedName("validationErrors")
	var validationErrors: List<String> = emptyList()

)