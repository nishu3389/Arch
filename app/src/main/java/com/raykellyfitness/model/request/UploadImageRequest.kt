package com.raykellyfitness.model.request

import com.google.gson.annotations.SerializedName

data class UploadImageRequest(
    @field:SerializedName("Base64Image")
    var Base64Image: String = ""
)