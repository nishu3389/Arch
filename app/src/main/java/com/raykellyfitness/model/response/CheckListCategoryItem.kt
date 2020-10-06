package com.raykellyfitness.model.response

import com.google.gson.annotations.SerializedName

data class CheckListCategoryItem(

	@field:SerializedName("checkListCategoryItem")
	var checkListCategoryItem: List<CheckListCategoryItemItem?>? = null,

	@field:SerializedName("checkListCategoryName")
	var checkListCategoryName: String? = null
)