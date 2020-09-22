package com.three.u.model.request

import com.google.gson.annotations.SerializedName


data class RequestForgotPassword(
	var email: String? = "",
	var PhoneNumber: String? = ""
)

data class RequestAddWeight(
	var height: String? = "",
	var weight: String? = ""
)
data class RequestAddSugar(
	var blood_sugar_fasting: String? = "",
	var blood_sugar_postprandial: String? = ""
)
data class RequestAddBloddPressure(
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