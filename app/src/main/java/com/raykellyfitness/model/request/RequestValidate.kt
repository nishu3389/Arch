package com.raykellyfitness.model.request

import com.google.gson.annotations.SerializedName


data class RequestValidate(

	@field:SerializedName("phoneNumber")
	var phoneNumber: String? = "",

	@field:SerializedName("countryCallingCode")
	var countryCallingCode: String? = "",

	@field:SerializedName("otp")
	var otp: String? = ""
)