package com.three.u.model.request

import com.google.gson.annotations.SerializedName

data class RequestChangePassword(

	@field:SerializedName("old_password")
	var oldPassword: String = "",

	@field:SerializedName("new_password")
	var newPassword: String = "",

	@field:SerializedName("ConfirmPassword")
	var confirmPassword: String = ""
)