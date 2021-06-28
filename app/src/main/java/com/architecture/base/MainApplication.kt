package com.architecture.base

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    var globalObservers = GlobalObservers()

    companion object {
        private lateinit var instance: MainApplication
        fun get(): MainApplication = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    fun getContext(): Context {
        return applicationContext
    }

}