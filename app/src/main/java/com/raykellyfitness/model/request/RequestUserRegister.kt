package com.raykellyfitness.model.request

import com.google.gson.annotations.SerializedName

data class RequestUserRegister(
	@field:SerializedName("Email")
	var email: String? = "",

	@field:SerializedName("PhoneNumber")
	var PhoneNumber: String? = "",

	@field:SerializedName("PhoneVerificationCode")
	var PhoneVerificationCode: String? = "",

	@field:SerializedName("Password")
	var password: String? = "",

	@field:SerializedName("CustomerName")
	var customerName: String? = "",

	@Transient var confirmPassword: String = "",

	@Transient var flagAccept : Boolean = false

)

data class RequestSendOtp(
	var PhoneNumber: String? = ""
)