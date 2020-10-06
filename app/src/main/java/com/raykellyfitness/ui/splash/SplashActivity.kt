package com.raykellyfitness.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.iid.FirebaseInstanceId
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.raykellyfitness.R
import com.raykellyfitness.ui.activity.AccountHandlerActivity
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.base.BaseActivity
import com.raykellyfitness.base.MainApplication
import com.raykellyfitness.base.sendToServer
import com.raykellyfitness.base.toast
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.util.Util


class SplashActivity : BaseActivity() {
    private val SPLASH_DURATION: Long = 200
    lateinit var mViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.setInstance(this)
        saveDeviceToken()
        var keyhash=Util.getKeyHash(this)
        setContentView(R.layout.activity_splash)
        Util.updateStatusBarColor("#F5333F",this as FragmentActivity)
        mViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        mViewModel.proceedAhead.observe(this, Observer {
            if (it) {
                val intent: Intent
                if (Prefs.get().loginData != null) {
//                    if (Prefs.get().loginData?.isProfileCompeled==true){
//                        Prefs.get().isFirstTimeAdvShown=false
//                        intent = Intent(this, MainBoardActivity::class.java)
//                    }else{
                        intent = Intent(this, HomeActivity::class.java)
//                    }
                } else {
                    intent = Intent(this, AccountHandlerActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        })


        TedPermission.with(this)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    Handler().postDelayed({ mViewModel.proceedAhead.value = true }, SPLASH_DURATION)
                }
                override fun onPermissionDenied(deniedPermissions: List<String>) {
                    "Permission Denied".toast()
                    finish()
                }
            })
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
            .check()


    }

    override fun requestLogout() {

    }


    private fun saveDeviceToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            val deviceToken = instanceIdResult.token
            Log.e("device token", deviceToken)
            Log.e("device token length ", deviceToken.length.toString())

            Prefs.get().deviceToken = deviceToken
            deviceToken.sendToServer()
        }
    }

}
