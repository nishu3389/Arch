package com.three.u.model.request
import com.google.gson.annotations.SerializedName
import com.three.u.util.Prefs


data class RequestLogin(

	@field:SerializedName("email")
	var Email: String ="",

	@field:SerializedName("password")
	var Password: String = "",

	@field:SerializedName("device_token")
	val DeviceToken: String = Prefs.get().deviceToken,

	@field:SerializedName("device_type")
	val DeviceType: String = "android",

	@Transient var flagRememberMe : Boolean = Prefs.get().rememberMe
)