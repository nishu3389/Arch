package com.demo.fcm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationBean(

	@field:SerializedName("id")
	val id: String? = "1",

	@field:SerializedName("type")
	val notificationtype: String? = "meal",

	@field:SerializedName("day")
	val day: String? = "Day 1",

	@field:SerializedName("title")
	val title: String? = "",

	@field:SerializedName("body")
	val body: String? = ""
) : Parcelable