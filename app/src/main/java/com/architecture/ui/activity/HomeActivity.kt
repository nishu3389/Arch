package com.architecture.ui.activity

import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.demo.fcm.NotificationBean
import com.architecture.R
import com.architecture.base.*
import com.architecture.databinding.ActivityMainBinding
import com.architecture.ui.splash.SplashActivity
import com.architecture.util.Constant
import com.architecture.util.Constant.NOTIFICATION_TYPE_POST
import com.architecture.util.Prefs
import com.architecture.util.permission.DeviceRuntimePermission
import com.architecture.util.permission.IPermissionGranted
import kotlinx.android.synthetic.main.content_main.*

class HomeActivity : BaseActivity(), NavController.OnDestinationChangedListener, IPermissionGranted {

    var notificationModel: NotificationBean? = null
    lateinit var context: Context
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    private val fragmentsTabs = listOf(R.id.HomeFragment)
    lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWork()
        otherWork()
    }


    fun showLogo(show: Boolean) {
        if (show) mBinding.imgLogo.visible()
        else mBinding.imgLogo.gone()
    }

    fun showBack(show: Boolean) {
        if (show) mBinding.imgBack.visible()
        else mBinding.imgBack.gone()
    }

    fun setTitle(title: String) {
        if (title.isEmptyy()) mBinding.tvTitle.gone()
        else {
            mBinding.tvTitle.visible()
            mBinding.tvTitle.text = title
        }
    }

    fun showLogoAndTitle(title: String) {
        mBinding.imgLogo.visible()
        mBinding.tvTitle.visible()
        mBinding.tvTitle.text = title
    }

    private fun otherWork() {
        backPressWork()
    }

    private fun backPressWork() {
        mBinding.imgBack.push()?.setOnClickListener {
            onBackPressed()
        }
    }


    private fun initWork() {
        context = this
        setStatusBarColor(R.color.white)
        intent?.let {
            if (intent != null && intent.extras != null && intent.extras!!.containsKey(Constant.BEAN)) {
                notificationModel = intent.getParcelableExtra(Constant.BEAN)
            }
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.apply {
            toolbar.title = ""
            setSupportActionBar(toolbar)
        }

        val drawerLayout: DrawerLayout = mBinding.drawerLayout
        navController = setNavigationController()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun setNavigationController(): NavController {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener(this)
        navController.addOnDestinationChangedListener(this)
        return navController
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {

    }

    override fun setTitle(title: CharSequence?) {
        mBinding.tvTitle.text = title
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            val f = getCurrentFragment(BaseFragment::class.java)
            f?.onActivityResult(requestCode, resultCode, data)
    }

    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {

            DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> {

            }
        }
    }

    override fun permissionDenied(requestCode: Int) {
        when (requestCode) {

            DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> checkAndAskPermission(
                DeviceRuntimePermission(DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA,
                                        null))
        }
    }

    override fun onBackPressed() {

        when {
            fragmentsTabs.contains(navController.currentDestination?.id) -> {
                backToHome()
                return
            }
            navController.currentDestination?.id == R.id.HomeFragment -> {
                finish()
            }
            else -> super.onBackPressed()
        }

    }

    private fun backToHome() {
        navController.popBackStack(R.id.HomeFragment, false)
    }

    override fun requestLogout() {
        Prefs.get().loginData?.user_name?.let {
            showAlertDialog(it,
                            getString(R.string.msg_app_logout_confirmation),
                            getString(R.string.yes),
                            getString(R.string.no),
                            DialogInterface.OnClickListener { _, which ->
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    mBaseViewModel.logOut()
                                        .observe(MainBoardActivity@ this, Observer {
                                            onLogOutSuccess()
                                        })
                                }
                            })
        }
    }

    private fun onLogOutSuccess() {
        Prefs.get().clear()
        startActivity(Intent(this, SplashActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        finish()
    }


}
