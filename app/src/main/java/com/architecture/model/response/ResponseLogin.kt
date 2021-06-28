package com.architecture.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseLogin(
    var created_at: String,
    var email: String,
    var height: String,
    var id: String,
    var is_active: String,
    var is_deleted: String,
    var name: String,
    var mobile: String,
    var address: String,
    var token: String,
    var user_name: String
) : Parcelable
