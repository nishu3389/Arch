package com.three.u.model.request

import com.google.gson.annotations.SerializedName

data class RequestSearchNearbyDistributor(

    @field:SerializedName("TextSearch")
    var textSearch: String? = "",

    @field:SerializedName("Lng")
    var lng: Double? = 27.7786693138345,

    @field:SerializedName("Lat")
    var lat: Double? = 26.9531091724948,

    @field:SerializedName("loactionAddress")
    var loactionAddress: String? = ""

)