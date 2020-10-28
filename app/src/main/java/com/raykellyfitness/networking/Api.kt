package com.raykellyfitness.networking

import com.google.gson.reflect.TypeToken
import com.raykellyfitness.model.request.*
import com.raykellyfitness.model.response.*
import com.raykellyfitness.model.response.ResponseGetProfile
import com.raykellyfitness.ui.meal.ResponseMealOuter
import com.raykellyfitness.ui.tipsandtricks.ResponseTipsDetail
import com.raykellyfitness.ui.tipsandtricks.ResponseTipsOuter

object Api {

    const val URL_DEMO_SERVER = "http://fitnesstrackerapp.projectstatus.in/api/"

    const val TERMS = "http://fitnesstrackerapp.projectstatus.in/welcome/terms_and_conditions"
    const val PRIVACY_POLICY = "http://fitnesstrackerapp.projectstatus.in/welcome/privacy_policy"

    //    const val TERMS = URL_DEV + "termsofuse"

    const val BASE_URL = URL_DEMO_SERVER

    const val UPDATE_TOKEN_TO_SERVER = "Account/UpdateDeviceToken"
    const val SIGNUP = ""
    const val SEND_VERIFICATION_CODE = ""
    const val LOGIN = "login"
    const val LOGOUT = "logout"
    const val FORGOT_PASSWORD = "forgot_password"
    const val RESET_PASSWORD = "reset_password"
    const val VERIFY_OTP = ""
    const val ADD_WEIGHT = "add_weight"
    const val ADD_BLOOD_SUGAR = "add_blood_sugar"
    const val ADD_BLOOD_PRESSURE = "add_blood_pressure"
    const val LIST_WEIGHT = "list_weight"
    const val LIST_BLOOD_SUGAR = "list_blood_sugar"
    const val LIST_BLOOD_PRESSURE = "list_blood_pressure"
    const val GET_POSTS_TIPS = "getPosts"
    const val GET_POSTS_MEAL = "getPosts"
    const val POST_DETAIL = "post_detail"

    const val API_POST_TYPE_TIPS = "tips"
    const val API_POST_TYPE_MEAL = "meal"
    const val API_POST_TYPE_EXERCISE = "exercise"
    const val API_POST_TYPE_MOTIVATION = "daily_motivation"
    const val API_POST_TYPE_BLOG = "blog"

    const val ContactUsQuery = ""
    const val ChangePassword = "change_password"
    const val SAVE_PAYMENT = "Authentication"
    const val Notifications = "get_notification"

    /*------------------------------PHASE 2 Sales Profile---------------------------------*/
    const val GETCUSTOMERPROFILE = ""
    /*------------------------------PHASE 2 Sales Profile---------------------------------*/


    fun getApiRequestType(url: String): ApiRequestType {

        val result = ApiRequestType()
        when (url) {

            LOGIN -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseLogin>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            VERIFY_OTP -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            SIGNUP -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            SEND_VERIFICATION_CODE -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            GETCUSTOMERPROFILE -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseGetProfile>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            LOGOUT -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            FORGOT_PASSWORD -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            RESET_PASSWORD -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ChangePassword -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ContactUsQuery -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ADD_WEIGHT -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseAddWeight>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ADD_BLOOD_SUGAR -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseAddBloodSugar>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ADD_BLOOD_PRESSURE -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseAddBloodPressure>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            LIST_WEIGHT -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseAddWeight>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            LIST_BLOOD_PRESSURE -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseAddBloodPressure>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            LIST_BLOOD_SUGAR -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseAddBloodSugar>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            GET_POSTS_TIPS -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseTipsOuter>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            GET_POSTS_MEAL -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseMealOuter>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            POST_DETAIL -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseTipsDetail>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            SAVE_PAYMENT -> {
                result.responseType = object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            Notifications -> {
                result.responseType = object : TypeToken<MasterResponse<ResponseNotifications>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }


        }
        throw IllegalStateException("API is not registered")
    }
    //a
}
