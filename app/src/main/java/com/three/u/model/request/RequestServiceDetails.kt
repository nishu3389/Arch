package com.three.u.model.request

import com.google.gson.annotations.SerializedName

data class RequestServiceDetails(

	@field:SerializedName("DirectoryId")
	var directoryId: Int? = 0,

	@field:SerializedName("Latitude")
	var latitude: Double? = 0.0,

	@field:SerializedName("Longitude")
	var longitude: Double? = 0.0
)