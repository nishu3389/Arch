package com.raykellyfitness.model.response

import com.google.gson.annotations.SerializedName

data class ResponseSearchServiceDirectory(

	@field:SerializedName("serviceDirectorId")
	val serviceDirectorId: Int? = null,

	val type: Int? = 0,

	@field:SerializedName("currentRating")
	val currentRating: String? = null,

	@field:SerializedName("serviceDirectoryLogo")
	val serviceDirectoryLogo: String? = null,

	@field:SerializedName("distance")
	val distance: String? = null,

	@field:SerializedName("latitude")
	val latitude: Double? = null,

	@field:SerializedName("serviceDirectoryId")
	val serviceDirectoryId: Int? = null,

	@field:SerializedName("directoryName")
	val directoryName: String? = null,

	@field:SerializedName("longitude")
	val longitude: Double? = null
)

