package com.raykellyfitness.model.response

import com.google.gson.annotations.SerializedName

data class CheckListCategoryItemItem(

	@field:SerializedName("itemID")
	var itemID: Int? = null,

	@field:SerializedName("itemName")
	var itemName: String? = null,

	@field:SerializedName("checkListLogoId")
	var checkListLogoId: Int? = null,

	@field:SerializedName("isChecked")
	var isChecked: Boolean? = null,

	@field:SerializedName("checkListLogo")
	var checkListLogo: String? = null
)