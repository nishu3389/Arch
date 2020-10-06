package com.raykellyfitness.model.request

import com.google.gson.annotations.SerializedName


data class RequestVerifyOTP(

    @field:SerializedName("VerificationCode")
    var otp: String? = "",
    @field:SerializedName("PhoneNumber")
    var PhoneNumber: String? = "",
    @field:SerializedName("NewPassword")
    var NewPassword: String? = ""

)