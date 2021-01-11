package com.raykellyfitness.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.raykellyfitness.ui.subscription.SubsCompleteListener
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    var billingClient: BillingClient? = null
    var globalObservers = GlobalObservers()

    companion object {
        private lateinit var instance: MainApplication
        fun get(): MainApplication = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    fun getInAppBillingClient(listener: SubsCompleteListener?) : BillingClient?{

            billingClient = BillingClient.newBuilder(getContext())
                .enablePendingPurchases()
                .setListener{ billingResult, purchases -> listener?.onSubsCompleted(billingResult, purchases) }
                .build()

            billingClient?.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK)
                       listener?.onConnected(billingResult)
                }

                override fun onBillingServiceDisconnected() {
                    listener?.onDisconnected()
                }
            })

            return billingClient
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