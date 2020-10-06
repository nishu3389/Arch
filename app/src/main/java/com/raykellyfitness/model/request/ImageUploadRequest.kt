package com.raykellyfitness.model.request

import com.google.gson.annotations.SerializedName


class ImageUploadRequest {
    @SerializedName("Base64Image")
    var Base64Image: String? = ""

}