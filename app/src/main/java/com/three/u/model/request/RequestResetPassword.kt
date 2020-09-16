package com.three.u.model.request

import com.google.gson.annotations.SerializedName


data class RequestResetPassword(

    @field:SerializedName("oldPassword")
    var oldPassword: String = "",

    @field:SerializedName("newPassword")
    var newPassword: String = "",

    @field:SerializedName("confirmPassword")
    var confirmPassword: String = ""


)