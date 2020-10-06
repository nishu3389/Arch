package com.raykellyfitness.model.response

import com.google.gson.annotations.SerializedName

data class ResponseScanAdvertisementQRCode(
    @SerializedName("websiteAppLink")
    var websiteAppLink: String? =""
)