package com.raykellyfitness.model.request


import com.google.gson.annotations.SerializedName

data class RequestDeleteSharedUser(
    @SerializedName("ChecklistSharedWithId")
    var checklistSharedWithId: Int
)