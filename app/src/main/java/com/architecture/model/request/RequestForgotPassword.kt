package com.architecture.model.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class RequestSavePayment(
    var receiptData : ReceiptData?,
    var product_id: String? = "",
    var method: String? = "save_payment"
)

data class ReceiptData(
    val acknowledged: Boolean,
    val autoRenewing: Boolean,
    val orderId: String,
    val packageName: String,
    val productId: String,
    val purchaseState: Int,
    val purchaseTime: Long,
    val purchaseToken: String
)

data class RequestForgotPassword(
	var email: String? = "",
	var PhoneNumber: String? = ""
)


data class RequestAddWeight(
	var height: String? = "",
	var weight: String? = ""
)
data class RequestAddBloodSugar(
	var blood_sugar_fasting: String? = "",
	var blood_sugar_postprandial: String? = ""
)
data class RequestAddBloodPressure(
	var blood_pressure_diastolic: String? = "",
	var blood_pressure_systolic: String? = ""
)

class ResponseAddWeight : ArrayList<ResponseAddWeightItem>()

data class ResponseAddWeightItem(
    val created_at: String,
    val dayno: String,
    val height: String,
    val weight: String
)

class ResponseAddBloodSugar : ArrayList<ResponseAddBloodSugarItem>()

data class ResponseAddBloodSugarItem(
    val blood_sugar_fasting: String,
    val blood_sugar_postprandial: String,
    val dayno: String,
    val created_at: String = "2020-09-11"
)

class ResponseAddBloodPressure : ArrayList<ResponseAddBloodPressureItem>()

data class ResponseAddBloodPressureItem(
    val blood_pressure_diastolic: String,
    val blood_pressure_systolic: String,
    val dayno: String,
    val created_at: String = "2020-09-11"
)

class ResponseNotifications : ArrayList<Notification>()

@Parcelize
data class Notification(
    val day: String = "",
    val post_id: String = "1",
    val message: String = "New chest workout available to try, we hope you may like it, click here to have a look on it.",
    val type: String = "meal",
    val created: String = "2020-09-11 00:00:00"
) : Parcelable

data class RequestNotifications(
    var type: String? = "",
    var method: String? = ""
)
