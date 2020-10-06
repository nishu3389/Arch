package com.raykellyfitness.model.response

import com.google.gson.annotations.SerializedName

data class ResponseSearchNearbyDistributor(

	@field:SerializedName("lng")
	var lng: Double? = 0.0,

	@field:SerializedName("distance")
	var distance: String? = "",

	@field:SerializedName("contact")
	var contact: String? = "",

	@field:SerializedName("profilePic")
	var profilePic: String? = "",

	@field:SerializedName("name")
	var name: String? = "",

	@field:SerializedName("id")
	var id: Int? = 0,

	@field:SerializedName("lat")
	var lat: Double? = 0.0
)