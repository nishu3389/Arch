package com.raykellyfitness.model.request


import com.google.gson.annotations.SerializedName

data class RequestSharedUsers(
    @SerializedName("ChecklistCustomerId")
    var checklistCustomerId: Int
)

class RequestAddExpense {
    @SerializedName("ChecklistId")
    var ChecklistId: Int = 0

    @SerializedName("ExpenseCategory")
    var ExpenseCategory: Int = 0

    @SerializedName("Amount")
    var Amount: Double = 0.0
}