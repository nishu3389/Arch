package com.demo.fcm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationBean(

	@field:SerializedName("post_id")
	val postId: String? = "",

	@field:SerializedName("type")
	val type: String? = "",

	@field:SerializedName("notification_type")
	var notificationType: String? = "",

	@field:SerializedName("day")
	val day: String? = "",

	@field:SerializedName("message")
	val message: String? = ""

) : Parcelable