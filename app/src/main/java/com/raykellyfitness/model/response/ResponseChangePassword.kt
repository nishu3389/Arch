package com.raykellyfitness.model.response

import com.google.gson.annotations.SerializedName


data class ResponseChangePassword(

	@field:SerializedName("myFavorite")
	val myFavorite: Boolean? = null

)