package com.three.u.model.response


import com.google.gson.annotations.SerializedName

data class ResponseBudgetDetail(
    @SerializedName("expensesList")
    var expensesList: List<Expenses>,
    @SerializedName("remainingBudget")
    var remainingBudget: String,
    @SerializedName("totalBudget")
    var totalBudget: String = "0.0"
) {
    data class Expenses(
        @SerializedName("amount")
        var amount: String,
        @SerializedName("date")
        var date: String,
        @SerializedName("expenseCategory")
        var expenseCategory: String
    )
}

data class RequestBudgetDetail(
    @SerializedName("CustomerChecklistId")
    var CustomerChecklistId: Int
)