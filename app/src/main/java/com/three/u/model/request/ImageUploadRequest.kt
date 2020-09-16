package com.three.u.model.request

import com.google.gson.annotations.SerializedName
import java.util.ArrayList


class ImageUploadRequest {
    @SerializedName("Base64Image")
    var Base64Image: String? = ""

}