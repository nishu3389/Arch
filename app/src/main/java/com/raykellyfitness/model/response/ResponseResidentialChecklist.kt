package com.raykellyfitness.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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