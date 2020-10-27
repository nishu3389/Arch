package com.raykellyfitness.ui.subscription

import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase

interface SubsCompleteListener {
    fun onSubsCompleted(var1 : BillingResult, var2 : List<Purchase>?)
    fun onConnected(var1 : BillingResult)
    fun onDisconnected()
}