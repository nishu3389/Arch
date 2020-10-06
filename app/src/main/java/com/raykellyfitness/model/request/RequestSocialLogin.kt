package com.raykellyfitness.model.request
import com.google.gson.annotations.SerializedName
import com.raykellyfitness.util.Prefs


data class RequestSocialLogin(


	@field:SerializedName("DeviceToken")
	var deviceToken: String? =Prefs.get().deviceToken,

	@field:SerializedName("GoogleId")
	var googleId: String? = "",

	@field:SerializedName("FacebookId")
	var facebookId: String? = "",

	@field:SerializedName("TwitterId")
    var twitterId: String? = "",

	@field:SerializedName("LinkedinId")
    var linkedinId: String? = "",

	@field:SerializedName("CustomerName")
	var customerName: String? = "",

	@field:SerializedName("email")
	var email: String? = "",

	@field:SerializedName("LogoProfilePic")
	var LogoProfilePic: String? = ""

)