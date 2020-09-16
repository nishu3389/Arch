package com.three.u.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.three.u.R
import com.three.u.base.BaseActivity
import com.three.u.base.BaseFragment
import com.three.u.base.MainApplication
import com.three.u.base.getCurrentFragment
import com.three.u.databinding.ActivityAccountHandlerBinding
import com.three.u.login.LoginFragment
import com.three.u.util.Prefs

class AccountHandlerActivity : BaseActivity() {

    lateinit var mBinding: ActivityAccountHandlerBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, com.three.u.R.layout.activity_account_handler)
        navController = setNavigationController()

        MainApplication.setInstance(this)
        if (!Prefs.get().isIntroShown){
            navController.navigate(R.id.IntroSliderFragment)
        }

    }

    override fun requestLogout() {

    }

    private fun setNavigationController(): NavController {
        val navController = Navigation.findNavController(this, com.three.u.R.id.main_nav_fragment)
        navController.addOnDestinationChangedListener { _, _, _ -> hideKeyboard() }
        return navController
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            val f = getCurrentFragment(BaseFragment::class.java)
            f?.onActivityResult(requestCode, resultCode, data)
    }

}


