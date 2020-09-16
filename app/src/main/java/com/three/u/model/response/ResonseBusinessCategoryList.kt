package com.three.u.model.response

import com.google.gson.annotations.SerializedName

data class ResonseBusinessCategoryList(

	@field:SerializedName("id")
	var id: Int? = 0,

	@field:SerializedName("categoryName")
	var categoryName: String? = ""
)