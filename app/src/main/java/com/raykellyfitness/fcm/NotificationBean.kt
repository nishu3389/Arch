package com.demo.fcm

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationBean(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("notificationtype")
	val notificationtype: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null
) : Parcelable


class NotificationType{
	companion object {
		val None: Int? = 0
		val OrderPlaced: Int? = 1
		val OrderCancelled: Int? = 2
		val ItemDelivered: Int? = 3
		val ChecklistUpdate: Int? = 4
		val BoxAllotment: Int? = 5
		val ConceirgeProcessed: Int? = 6

		val ChecklistComplete: Int? = 8
		val BudgetConsumed: Int? = 9


	}
}