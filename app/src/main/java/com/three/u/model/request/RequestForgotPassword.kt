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