package com.three.u.model.request

import com.google.gson.annotations.SerializedName

data class RequestScanAdvertisementQRCode(

    @field:SerializedName("BoxAdvertisementId")
    var BoxAdvertisementId: Int? = 0,

    @field:SerializedName("Latitude")
    var Latitude: Double? = 0.0,

    @field:SerializedName("Longitude")
    var Longitude: Double? = 0.0
)