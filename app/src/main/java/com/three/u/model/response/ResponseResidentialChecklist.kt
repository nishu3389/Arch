package com.three.u.model.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.three.u.model.request.ListOfFile
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class ResponseResidentialChecklist(
    var name: String? = "Make travel arrangements",
    var date: String? = "10 June 2020",
    var progress: String? = "49%",
    var id: String? = "1"
) : Parcelable {

    /* @SerializedName("name")
     var name: String? = ""

     @SerializedName("date")
     var date: String? = ""

     @SerializedName("progress")
     var progress: String? = ""

     @SerializedName("id")
     var id: String? = ""*/

}