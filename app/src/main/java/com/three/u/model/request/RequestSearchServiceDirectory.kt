package com.three.u.model.request

import com.google.gson.annotations.SerializedName

data class RequestSearchServiceDirectory(

	@field:SerializedName("Address")
	var address: String? = "",

	@field:SerializedName("PageSize")
	var pageSize: Int? = 10,

	@field:SerializedName("PageNumber")
	var pageNumber: Int? = 1,

	@field:SerializedName("Latitude")
	var latitude: Double? = 0.0,

	@field:SerializedName("BusinessCategoryId")
	var businessCategoryId: Int? = 0,

	@field:SerializedName("BusinessCategoryName")
	var businessCategoryName: String? ="",

	@field:SerializedName("DirectoryName")
	var directoryName: String? = "",

	@field:SerializedName("Longitude")
	var longitude: Double? = 0.0
)