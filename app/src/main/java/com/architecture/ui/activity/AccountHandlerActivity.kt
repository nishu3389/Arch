package com.architecture.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.architecture.R
import com.architecture.base.BaseActivity
import com.architecture.base.BaseFragment
import com.architecture.base.getCurrentFragment
import com.architecture.databinding.ActivityAccountHandlerBinding

class AccountHandlerActivity : BaseActivity() {

    lateinit var mBinding: ActivityAccountHandlerBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, com.architecture.R.layout.activity_account_handler)
        navController = setNavigationController()

        navController.navigate(R.id.LoginFragment)
    }

    override fun requestLogout() {

    }

    private fun setNavigationController(): NavController {
        val navController = Navigation.findNavController(this, com.architecture.R.id.main_nav_fragment)
        navController.addOnDestinationChangedListener { _, _, _ -> hideKeyboard() }
        return navController
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            val f = getCurrentFragment(BaseFragment::class.java)
            f?.onActivityResult(requestCode, resultCode, data)
    }

}


