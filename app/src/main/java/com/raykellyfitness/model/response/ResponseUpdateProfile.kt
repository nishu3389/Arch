package com.raykellyfitness.model.response

import com.google.gson.annotations.SerializedName

data class ResponseUpdateProfile(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("logoProfilePic")
	val logoProfilePic: String? = null,

	@field:SerializedName("authToken")
	val authToken: String? = null,

	@field:SerializedName("customerName")
	val customerName: String? = null,

	@field:SerializedName("customerAddress")
	val customerAddress: String? = null
)

data class ResponseUpdateSalesProfile(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("logoProfilePic")
	val logoProfilePic: String? = null,

	@field:SerializedName("authToken")
	val authToken: String? = null,

	@field:SerializedName("customerName")
	val customerName: String? = null,

	@field:SerializedName("customerAddress")
	val customerAddress: String? = null
)