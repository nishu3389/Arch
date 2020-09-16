package com.three.u.model.request

import com.google.gson.annotations.SerializedName


data class RequestForgotPassword(
	var email: String? = "",
	var PhoneNumber: String? = ""
)