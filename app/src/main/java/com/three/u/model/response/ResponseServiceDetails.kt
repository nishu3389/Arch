package com.three.u.model.response

import com.google.gson.annotations.SerializedName

data class ResponseServiceDetails(

    @field:SerializedName("currentRating")
    var currentRating: String? = null,

    @field:SerializedName("serviceDirectoryLogo")
    var serviceDirectoryLogo: String? = "",

    @field:SerializedName("websiteAppLink")
    var websiteAppLink: String? = null,

    @field:SerializedName("address")
    var address: String? = null,

    @field:SerializedName("distance")
    var distance: String? = null,

    @field:SerializedName("latitude")
    var latitude: Double? = 0.0,

    @field:SerializedName("areaOfOperation")
    var areaOfOperation: String? = null,

    @field:SerializedName("serviceDirectoryId")
    var serviceDirectoryId: Int? = null,

    @field:SerializedName("directoryCategory")
    var directoryCategory: String? = null,

    @field:SerializedName("serviceDirectorId")
    var serviceDirectorId: Int? = null,

    @field:SerializedName("contactNumber")
    var contactNumber: String? = null,

    @field:SerializedName("directoryName")
    var directoryName: String? = null,

    @field:SerializedName("pricing")
    var pricing: String? = null,

    @field:SerializedName("longitude")
    var longitude: Double? = 0.0,
    @field:SerializedName("businessName")
    var businessName: String? = ""

)