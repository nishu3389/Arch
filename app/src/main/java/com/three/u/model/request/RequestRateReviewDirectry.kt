package com.three.u.model.request

import com.google.gson.annotations.SerializedName
data class RequestRateReviewDirectry(

	@field:SerializedName("DirectoryId")
	var DirectoryId: Int = 0,
	@field:SerializedName("Rate")
	var Rate: Int =0,
	@field:SerializedName("Comment")
	var Comment: String = ""

)