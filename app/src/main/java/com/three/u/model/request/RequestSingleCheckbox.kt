package com.three.u.model.request

import com.google.gson.annotations.SerializedName

data class RequestSingleCheckbox(
    @SerializedName("IsChecked")
    var isChecked: Int,
    @SerializedName("ItemId")
    var itemId: Int
)

data class RequestMultipleCheckbox(
    @SerializedName("IsChecked")
    var isChecked: Int,
    @SerializedName("ItemId")
    var itemIds: List<Int>
)

data class RequestSingleToDo(
    @SerializedName("IsChecked")
    var isChecked: Int,
    @SerializedName("ItemId")
    var itemId: Int
)

class RequestOTP{
    @SerializedName("ChecklistCustomerId")
    var checklistCustomerId: Int = 0
    @SerializedName("Email")
    var email: String = ""
    @SerializedName("PhoneNumber")
    var phoneNumber: String = ""
    @SerializedName("VerificationCode")
    var verificationCode: String = ""
}

