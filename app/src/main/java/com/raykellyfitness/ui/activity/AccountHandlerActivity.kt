package com.raykellyfitness.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.raykellyfitness.R
import com.raykellyfitness.base.BaseActivity
import com.raykellyfitness.base.BaseFragment
import com.raykellyfitness.base.MainApplication
import com.raykellyfitness.base.getCurrentFragment
import com.raykellyfitness.databinding.ActivityAccountHandlerBinding
import com.raykellyfitness.util.Prefs

class AccountHandlerActivity : BaseActivity() {

    lateinit var mBinding: ActivityAccountHandlerBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, com.raykellyfitness.R.layout.activity_account_handler)
        navController = setNavigationController()

        MainApplication.setInstance(this)
        if (!Prefs.get().isIntroShown)
            navController.navigate(R.id.IntroSliderFragment)
        else
            navController.navigate(R.id.LoginFragment)

    }

    override fun requestLogout() {

    }

    private fun setNavigationController(): NavController {
        val navController = Navigation.findNavController(this, com.raykellyfitness.R.id.main_nav_fragment)
        navController.addOnDestinationChangedListener { _, _, _ -> hideKeyboard() }
        return navController
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            val f = getCurrentFragment(BaseFragment::class.java)
            f?.onActivityResult(requestCode, resultCode, data)
    }

}


