package com.three.u.model.request


import com.google.gson.annotations.SerializedName

class RequestShareChecklist{
    @SerializedName("ChecklistCustomerId")
    var checklistCustomerId: Int = 0
    @SerializedName("Email")
    var email: String = ""
    @SerializedName("Name")
    var name: String = ""
    @SerializedName("PhoneNumber")
    var phoneNumber: String = ""
}