package com.three.u.model.response

import com.google.gson.annotations.SerializedName

data class ResponseRedeemFreebox(

	@field:SerializedName("qrBase64Code")
	val qrBase64Code: String? = null,

	@field:SerializedName("alphanumericCode")
	val alphanumericCode: String? = null,

	@field:SerializedName("availableBoxes")
	val availableBoxes: Int? = null
)