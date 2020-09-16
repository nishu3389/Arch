package com.three.u.model.request


import com.google.gson.annotations.SerializedName

data class RequestDeleteSharedUser(
    @SerializedName("ChecklistSharedWithId")
    var checklistSharedWithId: Int
)