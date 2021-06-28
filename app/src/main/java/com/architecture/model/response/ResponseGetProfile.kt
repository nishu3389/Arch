package com.architecture.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGetProfile(

	@SerializedName("addressLine")
	var addressLine: String? = "",
	@SerializedName("age")
	var age: Int? = 0,
	@SerializedName("cityId")
	var cityId: Int? = 0,
	var isNotificationEnabled: Boolean? = false,
	@SerializedName("contact")
	var contact: String? = "",
	@SerializedName("countryId")
	var countryId: Int? = 0,
	@SerializedName("customerId")
	var customerId: Int? = 0,
	@SerializedName("customerName")
	var customerName: String? = "",
	@SerializedName("email")
	var email: String? = "",
	@SerializedName("gender")
	var gender: Int? = 0,
	@SerializedName("logoProfilePic")
	var logoProfilePic: String? = "",
	@SerializedName("postcode")
	var postcode: String? = "",
	@SerializedName("stateId")
	var stateId: Int? = 0,
	@SerializedName("suburb")
	var suburb: String? = ""
)

data class ResponseGetSalesProfile(

	@field:SerializedName("customerId")
	var customerId: Int? = 0,

	@field:SerializedName("email")
	var email: String? = null,

	@field:SerializedName("customerName")
	var customerName: String? = null,

	@field:SerializedName("logoProfilePic")
	var logoProfilePic: String? = null,

	@field:SerializedName("contact")
	var contact: String? = null,

	@field:SerializedName("age")
	var age: Int? = null,

	@field:SerializedName("address")
	var address: String? = null,

	@field:SerializedName("suburb")
	var suburb: String? = "",

	@field:SerializedName("city")
	var city: String? = "",

	@field:SerializedName("state")
	var state: String? = "",

	@field:SerializedName("country")
	var country: String? = "",

	@field:SerializedName("postcode")
	var postcode: String? = "",

	@field:SerializedName("gender")
	var gender: Int? = null
)