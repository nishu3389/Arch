package com.raykellyfitness.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ResponseCourierCompaniesItem() : Parcelable {

    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("emailAddress")
    var emailAddress: String? = ""

    @SerializedName("address")
    var address: String? = ""

    @SerializedName("companyName")
    var name: String? = ""

    @SerializedName("contact")
    var phone: String? = ""
}