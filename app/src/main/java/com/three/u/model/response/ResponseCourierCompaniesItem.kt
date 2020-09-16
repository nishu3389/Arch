package com.three.u.model.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.three.u.model.request.ListOfFile
import kotlinx.android.parcel.Parcelize
import java.util.*

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