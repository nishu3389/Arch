package com.raykellyfitness.ui.activity

import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
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
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.ActivityMainBinding
import com.raykellyfitness.ui.splash.SplashActivity
import com.raykellyfitness.util.Constant
import com.raykellyfitness.util.Constant.NOTIFICATION_TYPE_POST
import com.raykellyfitness.util.ParcelKeys
import com.raykellyfitness.util.ParcelKeys.PK_FROM
import com.raykellyfitness.util.ParcelKeys.PK_FROM_NOTIFICATION
import com.raykellyfitness.util.ParcelKeys.PK_POST_DAY
import com.raykellyfitness.util.ParcelKeys.PK_POST_ID
import com.raykellyfitness.util.ParcelKeys.PK_POST_TYPE
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.util.Validator
import com.raykellyfitness.util.permission.DeviceRuntimePermission
import com.raykellyfitness.util.permission.IPermissionGranted
import kotlinx.android.synthetic.main.content_main.*

class HomeActivity : BaseActivity(), NavController.OnDestinationChangedListener,
    IPermissionGranted {

    var notificationModel: NotificationBean? = null
    var fromNotification: Boolean = false
    private var arrTabTextView = ArrayList<TextView>()

    private val TAB_HOME = 1
    private val TAB_HEALTH = 2
    private val TAB_NOTIFICATION = 3
    private val TAB_SETTINGS = 4
    private val TAB_NONE = 6

    lateinit var context: Context
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    private val fragmentsWhereBottomTabsShouldBeShown =
        listOf(R.id.HomeFragment, R.id.SettingsFragment, R.id.NotificationsFragment)

    private val fragmentsTabs =
        listOf(R.id.SettingsFragment, R.id.HealthFragment, R.id.NotificationsFragment)

    lateinit var mBinding: ActivityMainBinding

    var instance: HomeActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWork()
        otherWork()
        setStatusBarColor("#ffffff")
    }

    var passwordVerified: Boolean = false

    private val INTENT_AUTHENTICATE: Int = 3487

    fun showRightLogo(show: Boolean) {
        if (show) mBinding.imgRight.visible()
        else mBinding.imgRight.gone()
    }

    fun showToolbar(show: Boolean) {
        if (show) mBinding.toolbar.visible()
        else mBinding.toolbar.gone()
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
        bottomNavigationWork()
        backPressWork()
    }

    private fun backPressWork() {
        mBinding.imgBack.push()?.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.d(" lifecycle", "onNewIntent")
    }

    private fun initWork() {
        MainApplication.setInstance(this)
        context = this
        intent?.let {
            if (intent != null && intent.extras != null && intent.extras!!.containsKey(Constant.BEAN)) {
                notificationModel = intent.getParcelableExtra(Constant.BEAN)
                fromNotification = true
            }
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.apply {
            toolbar.setTitle("")
            setSupportActionBar(toolbar)
        }

        val drawerLayout: DrawerLayout = mBinding.drawerLayout
        navController = setNavigationController()
        if (fromNotification) getDestination(notificationModel)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun bottomNavigationWork() {

        arrTabTextView = arrayListOf(tab_home, tab_health, tab_notification, tab_settings)

        tab_home.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.HomeFragment) return@setOnClickListener
            navController.navigate(R.id.HomeFragment)
            highlightTab(TAB_HOME)
        }

        tab_health.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.HealthFragment) return@setOnClickListener
            navController.navigate(R.id.HealthFragment)
            highlightTab(TAB_HEALTH)
        }

        tab_notification.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.NotificationsFragment) return@setOnClickListener
            navController.navigate(R.id.NotificationsFragment)
            highlightTab(TAB_NOTIFICATION)
        }

        tab_settings.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.SettingsFragment) return@setOnClickListener
            navController.navigate(R.id.SettingsFragment)
            highlightTab(TAB_SETTINGS)
        }


    }

    fun highlightHomeTab() {
        highlightTab(TAB_HOME)
    }

    private fun setNavigationController(): NavController {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener(this)
        navController.addOnDestinationChangedListener(this)
        return navController
    }

    override fun onDestinationChanged(controller: NavController,
                                      destination: NavDestination,
                                      arguments: Bundle?) {

        if (fragmentsWhereBottomTabsShouldBeShown.contains(destination.id)) {
            showBack(false)
            bottom_navigation_bar.visibility = View.VISIBLE
        } else {
            bottom_navigation_bar.visibility = View.GONE
            showBack(true)
        }

    }

    fun showBottomTabs() {
        bottom_navigation_bar.visibility = View.VISIBLE
    }

    fun hideBottomTabs() {
        bottom_navigation_bar.visibility = View.GONE
    }

    override fun setTitle(title: CharSequence?) {
        mBinding.tvTitle.setText(title)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        passwordVerified = true
        if (requestCode != INTENT_AUTHENTICATE) {
            val f = getCurrentFragment(BaseFragment::class.java)
            f?.onActivityResult(requestCode, resultCode, data)
        }

        if (requestCode == INTENT_AUTHENTICATE && resultCode != RESULT_OK) {
            Validator.showCustomToast("Authentication failed")
            finish()
        } else if (requestCode == INTENT_AUTHENTICATE && resultCode == RESULT_OK) {
            passwordVerified = true
            Log.d(" lifecycle", "passwordVerifiedTrueLockScreen")
        }

    }

    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {

            DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> {
                navController.navigate(R.id.ScanQRCodeFragment)
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

    var fromChecklistToServiceDirectory = false
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
        highlightTab(TAB_NONE)
        navController.popBackStack(R.id.HomeFragment, false)
    }

    private fun highlightTab(tab: Int) {
        when (tab) {
            TAB_HOME -> highlightTab(tab_home, false)
            TAB_HEALTH -> highlightTab(tab_health, false)
            TAB_NOTIFICATION -> highlightTab(tab_notification, false)
            TAB_SETTINGS -> highlightTab(tab_settings, false)
            TAB_NONE -> highlightTab(null, true)
        }
    }

    private fun highlightTab(tvTab: TextView?, clear: Boolean) {

        for (textView in arrTabTextView) setTvColor(textView, R.color.white)

        if (!clear) {
            setTvColor(tvTab, R.color.black_color)
        }

    }

    private fun getDestination(notification: NotificationBean?) {

        if (notification?.notificationType.equals(NOTIFICATION_TYPE_POST)) when (notification?.type) {

            Constant.POST_TYPE_BLOG -> navController.navigate(R.id.TipsDetailFragment,
                                                              bundleOf(PK_POST_ID to notification?.postId,
                                                                       PK_POST_TYPE to notification?.type,
                                                                       PK_POST_DAY to notification?.day,
                                                                       PK_FROM to PK_FROM))

            else -> navController.navigate(R.id.TipsAndTricksFragment, bundleOf(PK_POST_TYPE to notification?.type, PK_FROM to PK_FROM_NOTIFICATION))
        }
        else navController.navigate(R.id.SubscriptionFragment)
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
        startActivity(Intent(this,
                             SplashActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        finish()
    }


}
