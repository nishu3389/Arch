package com.three.u.model.request

import com.google.gson.annotations.SerializedName

data class RequestChangePassword(

	@field:SerializedName("OldPassword")
	var oldPassword: String = "",

	@field:SerializedName("NewPassword")
	var newPassword: String = "",

	@field:SerializedName("ConfirmPassword")
	var confirmPassword: String = ""
)