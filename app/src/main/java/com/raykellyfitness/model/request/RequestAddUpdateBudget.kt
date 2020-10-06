package com.raykellyfitness.model.request


import com.google.gson.annotations.SerializedName

data class RequestAddUpdateBudget(
    @SerializedName("Amount")
    var amount: Double,
    @SerializedName("CustomerChecklistId")
    var customerChecklistId: Int
)

class ResponseExpenseCategory : ArrayList<ResponseExpenseCategory.ResponseExpenseCategoryItem>(){
    data class ResponseExpenseCategoryItem(
        @SerializedName("categoryName")
        var categoryName: String,
        @SerializedName("id")
        var id: Int
    )
}