package com.raykellyfitness.model.request

import com.google.gson.annotations.SerializedName

data class RequestContectUs(

	@field:SerializedName("Email")
	var email: String? = "",

	@field:SerializedName("Query")
	var query: String? = "",

	@field:SerializedName("Name")
	var name: String? = "",

	@field:SerializedName("Contact")
	var contact: String? = ""
)