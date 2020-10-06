package com.raykellyfitness.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGetMasterCheckList(

	@field:SerializedName("checkListCategory")
	var checkListCategory: List<CheckListCategoryItem?>? = null,

	@field:SerializedName("typeofMove")
	var typeofMove: String? = null
)