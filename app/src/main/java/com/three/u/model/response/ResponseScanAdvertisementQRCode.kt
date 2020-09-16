package com.three.u.model.response

import com.google.gson.annotations.SerializedName

data class ResponseScanAdvertisementQRCode(
    @SerializedName("websiteAppLink")
    var websiteAppLink: String? =""
)