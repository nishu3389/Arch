package com.three.u.model.request

import com.google.gson.annotations.SerializedName

data class RequestChangeChecklistItemStatus(

	@field:SerializedName("ItemId")
	val itemId: Int? = 0,

	@field:SerializedName("IsChecked")
	val isChecked: Boolean? = false
)