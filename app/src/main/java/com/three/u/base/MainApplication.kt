package com.three.u.base

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.androidnetworking.AndroidNetworking
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import com.rohitss.uceh.UCEHandler
import com.stripe.android.PaymentConfiguration
import com.three.u.BuildConfig
import com.three.u.util.Constant.STRIPE_PUBLISHABLE_KEY
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@HiltAndroidApp
class MainApplication : Application() {

    /* API key:
    c6PW2wpERjAGAReMFVdRjgKtC

    API secret key:
    fan32fs7n4cVdKWhojFPLda8sDYM8aIEVIzUeidaP9SiQnul9P*/

    val twitter_CONSUMER_KEY = "c6PW2wpERjAGAReMFVdRjgKtC"
    val twitter_CONSUMER_SECRET = "fan32fs7n4cVdKWhojFPLda8sDYM8aIEVIzUeidaP9SiQnul9P"

    companion object {

        private var width = Resources.getSystem().getDisplayMetrics().widthPixels;
        private var height = Resources.getSystem().getDisplayMetrics().heightPixels;

        fun widthPercent(percent: Double): Int {
            val d = width * percent / 100
            return d.toInt()
        }

        fun heightPercent(percent: Double): Int {
            val d = height * percent / 100
            return d.toInt()
        }

        private lateinit var instance: MainApplication
        private lateinit var activityInstance: BaseActivity
        fun get(): MainApplication = instance
        fun setInstance(activity: BaseActivity) {
            activityInstance = activity
        }

        fun getActivityInstance(): BaseActivity {
            return activityInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        UCEHandler.Builder(applicationContext).build()

        var okHttpClient: OkHttpClient? = null
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient = OkHttpClient().newBuilder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build()
        }else
            okHttpClient = OkHttpClient().newBuilder().build()

        AndroidNetworking.initialize(applicationContext, okHttpClient)

        PaymentConfiguration.init(
            applicationContext,
            STRIPE_PUBLISHABLE_KEY
        )

    }

    fun getContext(): Context {
        return applicationContext
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
    }

}