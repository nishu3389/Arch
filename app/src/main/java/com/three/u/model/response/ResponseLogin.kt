package com.three.u.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseLogin(
    val created_at: String,
    val email: String,
    val height: String,
    val id: String,
    val is_active: String,
    val is_deleted: String,
    val name: String,
    val mobile: String,
    val address: String,
    val token: String,
    val user_name: String
) : Parcelable
