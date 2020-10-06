package com.raykellyfitness.model.request

import com.google.gson.annotations.SerializedName


data class RequestResetPassword(

    @field:SerializedName("otp")
    var otp: String = "",

    @field:SerializedName("new_password")
    var newPassword: String = "",

    @field:SerializedName("confirmPassword")
    var confirmPassword: String = ""

)