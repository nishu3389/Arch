package com.three.u.model.request

import com.google.gson.annotations.SerializedName

data class RequestChangeToDoChecklistItemStatus(

	@field:SerializedName("ItemId")
	val itemId: Int? = null,

	@field:SerializedName("IsChecked")
	val isChecked: Boolean? = null
)