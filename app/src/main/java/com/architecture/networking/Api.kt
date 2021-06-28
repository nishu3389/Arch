package com.architecture.networking

import com.google.gson.reflect.TypeToken
import com.architecture.model.response.MasterResponse
import com.architecture.model.response.ResponseGetProfile
import com.architecture.model.response.ResponseLogin

object Api {

    const val URL_LIVE = "https://raykellyfitness.com.au/api/"
    const val TERMS = "https://raykellyfitness.com.au/Terms"
    const val PRIVACY_POLICY = "https://raykellyfitness.com.au/Privacy"

    const val BASE_URL = URL_LIVE

    const val UPDATE_TOKEN_TO_SERVER = "Account/UpdateDeviceToken"
    const val SIGNUP = ""
    const val SEND_VERIFICATION_CODE = ""
    const val LOGIN = "login"
    const val LOGOUT = "logout"
    const val FORGOT_PASSWORD = "forgot_password"
    const val RESET_PASSWORD = "reset_password"
    const val VERIFY_OTP = ""

    const val ContactUsQuery = ""
    const val ChangePassword = "change_password"
    const val SAVE_PAYMENT = "Authentication"

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

        }
        throw IllegalStateException("API is not registered")
    }

}
