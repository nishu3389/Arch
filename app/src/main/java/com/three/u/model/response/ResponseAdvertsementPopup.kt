package com.three.u.model.response

import com.google.gson.annotations.SerializedName
import com.three.u.model.request.ListOfFile
import java.util.*

data class ResponseAdvertsementPopup(


    @SerializedName("advertisementId")
    var advertisementId: Int? = null,

    @SerializedName("advertisementURL")
    var advertisementURL: String? = "",

    @SerializedName("advertLink")
    var advertLink: String? = "",

    @SerializedName("residentialCount")
    var residentialCount: Int? = 0,

    @SerializedName("businessCount")
    var businessCount: Int? = 0

)