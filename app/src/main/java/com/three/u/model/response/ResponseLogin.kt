package com.three.u.model.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseLogin(

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("phoneNumber")
    var phoneNumber: String? = null,

    @field:SerializedName("logoProfilePic")
    var logoProfilePic: String? = null,

    @field:SerializedName("authToken")
    var authToken: String? = null,

    @field:SerializedName("customerName")
    var customerName: String? = null,

    @field:SerializedName("isProfileCompeled")
    var isProfileCompeled: Boolean? = null,

    @field:SerializedName("hasRedeemedFreeBox")
    var hasRedeemedFreeBox: Boolean? = null,

    @field:SerializedName("checkCompletionPercentage")
    var checkCompletionPercentage: Int? = 0

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(logoProfilePic)
        parcel.writeString(authToken)
        parcel.writeString(customerName)
        parcel.writeValue(isProfileCompeled)
        parcel.writeValue(hasRedeemedFreeBox)
        parcel.writeValue(checkCompletionPercentage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseLogin> {
        override fun createFromParcel(parcel: Parcel): ResponseLogin {
            return ResponseLogin(parcel)
        }

        override fun newArray(size: Int): Array<ResponseLogin?> {
            return arrayOfNulls(size)
        }
    }

}