package com.three.u.model.response

import com.google.gson.annotations.SerializedName

data class MasterResponse<T>(

	@field:SerializedName("data")
	var data: T? = null,

	@field:SerializedName("responseCode")
	var responseCode: Int = 0,

	@field:SerializedName("successMsg")
	var successMsg: String = "",

	@field:SerializedName("failureMsg")
	var failureMsg: String = "",

	@field:SerializedName("apiName")
	var apiName: String = "",

	@field:SerializedName("validationErrors")
	var validationErrors: List<String> = emptyList()
)