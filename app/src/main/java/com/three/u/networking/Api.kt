package com.three.u.networking

import com.google.gson.reflect.TypeToken
import com.three.u.model.request.*
import com.three.u.model.request.ResponseGetSalesProfile
import com.three.u.model.response.*
import com.three.u.model.response.ResponseGetProfile

object Api {

    const val URL_DEMO_SERVER = "http://fitnesstrackerapp.projectstatus.in/api/"

    const val TERMS = "http://3udemop2.projectstatus.in/termsofuse"

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


    const val ContactUsQuery = ""
    const val ChangePassword = "change_password"

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
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseGetProfile>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            LOGOUT -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            FORGOT_PASSWORD -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            RESET_PASSWORD -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ChangePassword -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ContactUsQuery -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ADD_WEIGHT -> {
                result.responseType =
                    object : TypeToken<MasterResponse<ResponseAddWeight>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ADD_BLOOD_SUGAR -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            ADD_BLOOD_PRESSURE -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            LIST_WEIGHT -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            LIST_BLOOD_PRESSURE -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }

            LIST_BLOOD_SUGAR -> {
                result.responseType =
                    object : TypeToken<MasterResponse<Boolean>>() {}.type
                result.url = BASE_URL
                result.requestType = RequestType.POST
                return result
            }


        }
        throw IllegalStateException("API is not registered")
    }
    //a
}
