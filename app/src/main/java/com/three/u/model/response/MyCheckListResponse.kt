package com.three.u.model.response

import com.google.gson.annotations.SerializedName


data class MyCheckListResponse(

    @SerializedName("masterChecklistItems")
    var masterChecklistItems: List<MasterChecklistItems>,
    @SerializedName("customerResponseToDo")
    var customerResponseToDo: List<CustomerResponseToDo>

)

data class AddCheckListResponse(

    @SerializedName("masterChecklistItems")
    var masterChecklistItems: List<MasterChecklistItems>,
    @SerializedName("customerRequestToDo")
    var customerResponseToDo: List<CustomerResponseToDo>
)

data class MasterChecklistItems(

    @SerializedName("typeofMove") val typeofMove: String,
    @SerializedName("checkListCategory") val checkListCategory: List<CheckListCategory>
)

data class CustomerResponseToDo(

    @SerializedName("itemId") val itemId: Int,
    @SerializedName("item") val item: String,
    @SerializedName("isChecked") val isChecked: Boolean
)

data class CheckListCategory(

    @SerializedName("checkListCategoryName") val checkListCategoryName: String,
    @SerializedName("checkListCategoryItem") val checkListCategoryItem: List<CheckListCategoryItem1>
)

data class CheckListCategoryItem1(

    @SerializedName("itemID") val itemID: Int,
    @SerializedName("isChecked") var isChecked: Boolean,
    @SerializedName("checkListLogo") val checkListLogo: String,
    @SerializedName("checkListLogoId") val checkListLogoId: Int,
    @SerializedName("itemName") val itemName: String
)