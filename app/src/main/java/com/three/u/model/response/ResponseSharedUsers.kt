package com.three.u.model.response


import com.google.gson.annotations.SerializedName

class ResponseSharedUsers : ArrayList<ResponseSharedUsers.ResponseSharedUsersItem>(){
    data class ResponseSharedUsersItem(
        @SerializedName("customerId")
        var customerId: Any?,
        @SerializedName("email")
        var email: String = "",
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("phoneNumber")
        var phoneNumber: String = ""
    )
}