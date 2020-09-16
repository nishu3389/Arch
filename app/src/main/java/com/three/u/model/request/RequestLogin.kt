package com.three.u.model.request
import com.google.gson.annotations.SerializedName
import com.three.u.util.Prefs


data class RequestLogin(

	@field:SerializedName("PhoneNumber")
	var PhoneNumber: String ="",

	@field:SerializedName("Email")
	var Email: String ="",

	@field:SerializedName("Password")
	var Password: String = "",

	@field:SerializedName("DeviceToken")
	val DeviceToken: String = Prefs.get().deviceToken,

//	@field:SerializedName("DeviceType")
//	val DeviceType: String = "Android",

	@Transient var flagRememberMe : Boolean = Prefs.get().rememberMe
)