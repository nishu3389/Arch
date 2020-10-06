package com.raykellyfitness.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RequestGetUserProfile(

	@field:SerializedName("UserId")
	var UserId: Int = 0

)

@Parcelize
data class RequestGetSalesProfile(

	@field:SerializedName("UserId")
	var UserId: Int = 0

) : Parcelable@Parcelize

data class RequestEnableNotification(

	var IsNotificationEnabled: Int = 0

) : Parcelable