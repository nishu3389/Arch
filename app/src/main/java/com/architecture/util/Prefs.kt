package com.architecture.util

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

import com.google.gson.Gson
import com.architecture.base.MainApplication
import com.architecture.model.request.RequestLogin
import com.architecture.model.response.ResponseGetProfile
import com.architecture.model.response.ResponseLogin

class Prefs {

    val PREF_SHUT_DOWN = "_SHUT_DOWN"
    val PREF_ADV_DATA = "_PREF_ADV_DATA"
    val PREF_SUBS_DATA = "_PREF_SUBS_DATA"
    val PREF_AUTH_TOKEN = "_PREF_AUTH_TOKEN"
    val PREF_LOGIN_DATA = "_PREF_LOGIN_DATA"
    val PREF_PROFILE_DATA = "_PREF_PROFILE_DATA"
    val PREF_NAME_GLOBAL = "_global_pref"
    val PREF_profileComplete = "_PREF_profileComplete"

    val PREF_SAVE_CREDENTAILS = "_PREF_SAVE_CREDENTAILS"
    val PREF_CREDENTAILS = "_PREF_CREDS"
    val PREF_TEMP_UPLOAD_RESPONSE = "_PREF_TEMP_UPLOAD_RESPONSE"
    val PREF_SELECT_LOCATION="_PREF_SELECT_LOCATION"
    val PREF_IS_INTRO_SHOWN="PREF_IS_INTRO_SHOWN"
    var PREFS_IS_CONGRTS_SHOWN="_PREFS_IS_CONGRTS_SHOWN"
    var PREFS_IS_FROM_SOCIAL_LOGIN="PREFS_IS_FROM_SOCIAL_LOGIN"
    var PREFS_LAST_LOCKED_MILLIS="PREFS_LAST_LOCKED_MILLIS"
    var PREFS_SHOULD_CHECK_SECURITY="PREFS_SHOULD_CHECK_SECURITY"
    var PREFS_IS_NOTIFICATIONS_ENABLED="_PREFS_IS_NOTIFICATIONS_ENABLED"
    var PREFS_IS_FIRST_TIME_ADV_POPUP="_PREFS_IS_FIRST_TIME_ADV_POPUP"
    val PREF_CHECK_LIST_PERCENT="_PREF_CHECK_LIST_PERCENT"
    private var sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MainApplication.get().getContext())

    init {
        instance = this
    }

    val gson = Gson()

    companion object {
        private var instance: Prefs? = null
        fun get(): Prefs {
            if (instance == null) {
                instance = Prefs()
            }
            return instance!!
        }
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    /*Authentication Token String for API Authentication*/
    /*var authenticationToken : String
    get() {return sharedPreferences.getString(PREF_AUTH_TOKEN,"") ?: ""}
    set(value) {sharedPreferences.edit().putString(PREF_AUTH_TOKEN,value).apply()}*/


    //Device Token, saved in separate SharedPreferences {PREF_NAME_GLOBAL} to persist data on user logout
    var deviceToken: String
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            return sF.getString(PREF_AUTH_TOKEN, "") ?: ""
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            sF.edit().putString(PREF_AUTH_TOKEN, value).apply()
        }

    var ADV_DATA: String
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_ADV_DATA, MODE_PRIVATE)
            return sF.getString(PREF_ADV_DATA, "") ?: ""
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_ADV_DATA, MODE_PRIVATE)
            sF.edit().putString(PREF_ADV_DATA, value).apply()
        }

    var SUBS_DATA: String
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_SUBS_DATA, MODE_PRIVATE)
            return sF.getString(PREF_SUBS_DATA, "") ?: ""
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_SUBS_DATA, MODE_PRIVATE)
            sF.edit().putString(PREF_SUBS_DATA, value).apply()
        }

    var SHUTDOWN: String
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_SHUT_DOWN, MODE_PRIVATE)
            return sF.getString(PREF_SHUT_DOWN, "") ?: ""
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_SHUT_DOWN, MODE_PRIVATE)
            sF.edit().putString(PREF_SHUT_DOWN, value).apply()
        }

    var isFirstTimeAdvShown: Boolean?
        get() {
            return  sharedPreferences.getBoolean(PREFS_IS_FIRST_TIME_ADV_POPUP, false)
        }
        set(value) {
            sharedPreferences.edit{
                if (value != null) {
                    putBoolean(PREFS_IS_FIRST_TIME_ADV_POPUP,value)
                }
            }
        }

    var lastLockedMillis: Long?
        get() {
            return  sharedPreferences.getLong(PREFS_LAST_LOCKED_MILLIS, 0)
        }
        set(value) {
            sharedPreferences.edit{
                if (value != null) {
                    putLong(PREFS_LAST_LOCKED_MILLIS,value)
                }
            }
        }

    var isCongrtsShown: Boolean?
        get() {
            return  sharedPreferences.getBoolean(PREFS_IS_CONGRTS_SHOWN, false)
        }
        set(value) {
            sharedPreferences.edit{
                if (value != null) {
                    putBoolean(PREFS_IS_CONGRTS_SHOWN,value)
                }
            }
        }

    var isFromSocialLogin: Boolean?
        get() {
            return  sharedPreferences.getBoolean(PREFS_IS_FROM_SOCIAL_LOGIN, false)
        }
        set(value) {
            sharedPreferences.edit{
                if (value != null) {
                    putBoolean(PREFS_IS_FROM_SOCIAL_LOGIN,value)
                }
            }
        }

    var shouldCheckSecurity: Boolean?
        get() {
            return  sharedPreferences.getBoolean(PREFS_SHOULD_CHECK_SECURITY, true)
        }
        set(value) {
            sharedPreferences.edit{
                if (value != null) {
                    putBoolean(PREFS_SHOULD_CHECK_SECURITY,value)
                }
            }
        }

    var isNotificationsEnabled: Boolean?
        get() {
            return  sharedPreferences.getBoolean(PREFS_IS_NOTIFICATIONS_ENABLED, false)
        }
        set(value) {
            sharedPreferences.edit{
                if (value != null) {
                    putBoolean(PREFS_IS_NOTIFICATIONS_ENABLED,value)
                }
            }
        }

    var checkListPercent: Int
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            return sF.getInt(PREF_CHECK_LIST_PERCENT, 0)
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            sF.edit().putInt(PREF_CHECK_LIST_PERCENT, value).apply()
        }

    var selectLocation: String
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            return sF.getString(PREF_SELECT_LOCATION, "") ?: ""
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            sF.edit().putString(PREF_SELECT_LOCATION, value).apply()
        }

    var isIntroShown: Boolean
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            return sF.getBoolean(PREF_IS_INTRO_SHOWN, false)
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            sF.edit().putBoolean(PREF_IS_INTRO_SHOWN, value).apply()
        }

    /*User Login com.architecture.model.response.Data received on successful Login*/
    var loginData: ResponseLogin?
        get() {
            val str = sharedPreferences.getString(PREF_LOGIN_DATA, "") ?: ""
            if (!str.isBlank()) return gson.fromJson(str, ResponseLogin::class.java)
            return null
        }
        set(value) {
            sharedPreferences.edit().putString(PREF_LOGIN_DATA, gson.toJson(value)).apply()
        }

    /*User Login com.architecture.model.response.Data received on successful Login*/
    var profileData: ResponseGetProfile?
        get() {
            val str = sharedPreferences.getString(PREF_PROFILE_DATA, "") ?: ""
            if (!str.isBlank()) return gson.fromJson(str, ResponseGetProfile::class.java)
            return null
        }
        set(value) {
            sharedPreferences.edit().putString(PREF_PROFILE_DATA, gson.toJson(value)).apply()
        }


    var isPRofileComplete: Boolean
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            return sF.getBoolean(PREF_profileComplete, false)
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            sF.edit().putBoolean(PREF_profileComplete, value).apply()
        }


    /*Flag to save User Credentials for AutoFill in Login*/
    var rememberMe: Boolean
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            return sF.getBoolean(PREF_SAVE_CREDENTAILS, false)
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            sF.edit().putBoolean(PREF_SAVE_CREDENTAILS, value).apply()
        }

    /*User Creds*/
    var userCreds: RequestLogin?
        get() {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            val str = sF.getString(PREF_CREDENTAILS, "") ?: ""
            if (!str.isBlank()) return gson.fromJson(str, RequestLogin::class.java)
            return null
        }
        set(value) {
            val sF = MainApplication.get().getContext()
                .getSharedPreferences(PREF_NAME_GLOBAL, MODE_PRIVATE)
            sF.edit().putString(PREF_CREDENTAILS, gson.toJson(value)).apply()
        }



}