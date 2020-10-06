package com.raykellyfitness.model.response

import com.google.gson.annotations.SerializedName

data class SellItemCategoryResponse(

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("category")
    var category: String? = null


)

class ResponseDonationBodies(
    @SerializedName("address")
    var address: String? = "",
    @SerializedName("agencyName")
    var agencyName: String? = "",
    @SerializedName("contact")
    var contact: String? = "",
    @SerializedName("emailAddress")
    var emailAddress: String? = "",
    @SerializedName("id")
    var id: Int? = null
)
