package com.architecture.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer

import com.google.firebase.iid.FirebaseInstanceId
import com.architecture.R
import com.architecture.base.*
import com.architecture.ui.activity.AccountHandlerActivity
import com.architecture.ui.activity.HomeActivity
import com.architecture.util.Prefs
import com.architecture.util.Util

class SplashActivity : BaseActivity() {

    private val SPLASH_DURATION: Long = 2000
    lateinit var mViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWork()
        otherWork()
    }

    private fun otherWork() {
        saveDeviceToken()

        mViewModel.proceedAhead.observe(this, Observer {
            if (it) {
                val intent: Intent

                if (Prefs.get().loginData != null)
                    intent = Intent(this, HomeActivity::class.java)
                else
                    intent = Intent(this, AccountHandlerActivity::class.java)

                startActivity(intent)
                finish()
            }
        })

        Handler(Looper.getMainLooper()).postDelayed({ mViewModel.proceedAhead.value = true }, SPLASH_DURATION)

    }

    private fun initWork() {
        setContentView(R.layout.activity_splash)
        var keyhash=Util.getKeyHash(this)
        Util.updateStatusBarColor(R.color.chuck_colorPrimaryDark,this as FragmentActivity)
        mViewModel = androidx.lifecycle.ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override fun requestLogout() {

    }


    private fun saveDeviceToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            val deviceToken = instanceIdResult.token
            Log.e("device token", deviceToken)
            Log.e("device token length ", deviceToken.length.toString())

            Prefs.get().deviceToken = deviceToken
//            deviceToken.sendToServer()
        }
    }

}
