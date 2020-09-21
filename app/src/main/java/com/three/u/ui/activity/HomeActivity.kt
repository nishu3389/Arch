package com.three.u.ui.activity

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
import com.demo.fcm.NotificationType
import com.three.u.R
import com.three.u.base.*
import com.three.u.databinding.ActivityMainBinding
import com.three.u.ui.splash.SplashActivity
import com.three.u.util.ParcelKeys
import com.three.u.util.Prefs
import com.three.u.util.Validator
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted
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

    private val fragmentsWhereBottomTabsShouldBeShown = listOf(
        R.id.HomeFragment,
        R.id.SettingsFragment,
        R.id.HealthFragment
    )


    private val fragmentsTabs = listOf(
        R.id.SettingsFragment,
        R.id.HealthFragment
    )

    lateinit var mBinding: ActivityMainBinding

    var instance: HomeActivity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWork()
        otherWork()
        MainApplication.setInstance(this)
        setStatusBarColor("#ffffff")
    }


    var passwordVerified: Boolean = false

    private val INTENT_AUTHENTICATE: Int = 3487

    fun showLogo(show: Boolean) {
        if (show)
            mBinding.imgLogo.visible()
        else
            mBinding.imgLogo.gone()
    }

    fun showBack(show: Boolean) {
        if (show)
            mBinding.imgBack.visible()
        else
            mBinding.imgBack.gone()
    }

    fun setTitle(title: String) {
        if (title.isEmptyy())
            mBinding.tvTitle.gone()
        else{
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
        context = this
        intent?.let {
            if (intent != null && intent.extras != null && intent.extras!!.containsKey("bean")) {
                notificationModel = intent.getParcelableExtra<NotificationBean>("bean")
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
        if (fromNotification)
            getDestination(notificationModel)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun bottomNavigationWork() {

        arrTabTextView = arrayListOf(
            tab_home,
            tab_health,
            tab_notification,
            tab_settings
        )

        tab_home.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.HomeFragment)
                return@setOnClickListener
            navController.navigate(R.id.HomeFragment)
            highlightTab(TAB_HOME)
        }

        tab_health.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.HealthFragment)
                return@setOnClickListener
            navController.navigate(R.id.HealthFragment)
            highlightTab(TAB_HEALTH)
        }

        tab_notification.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.HomeFragment)
                return@setOnClickListener
            navController.navigate(R.id.HomeFragment)
            highlightTab(TAB_NONE)
        }

        tab_settings.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.SettingsFragment)
                return@setOnClickListener
            navController.navigate(R.id.SettingsFragment)
            highlightTab(TAB_SETTINGS)
        }


    }

    fun highlightTabNone() {
//        highlightTab(TAB_NONE)
    }

    fun highlightHomeTab() {
        highlightTab(TAB_HOME)
    }

    fun highlightHealthTab() {
//        highlightTab(TAB_HEALTH)
    }

    fun highlightNotificationTab() {
//        highlightTab(TAB_NOTIFICATION)
    }

    fun highlightSettingsTab() {
//        highlightTab(TAB_SETTINGS)
    }


    private fun setNavigationController(): NavController {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener(this)
        navController.addOnDestinationChangedListener(this)
        return navController
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        /*when (destination.id) {
            R.id.HomeFragment -> {
                supportActionBar!!.setBackgroundDrawable(
                    ContextCompat.getDrawable(this, R.drawable.bg_home_toolbar_gradient)
                )
            }

            R.id.ProfileShowFragment -> {
                supportActionBar!!.setBackgroundDrawable(
                    ContextCompat.getDrawable(this, R.drawable.bg_profile_toolbar_gradient)
                )
            }
            R.id.ProfileEditFragment -> {

                if (Prefs.get().isPRofileComplete) {
                    supportActionBar!!.setBackgroundDrawable(
                        ContextCompat.getDrawable(this, R.drawable.bg_profile_toolbar_gradient)
                    )
                } else {

                    supportActionBar!!.setBackgroundDrawable(
                        ColorDrawable(Color.parseColor("#FFFFFF"))
                    )

                }

            }
            R.id.SalesProfileShowFragment -> {
                supportActionBar!!.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_profile_toolbar_gradient
                    )
                )
            }
            R.id.SalesProfileEditFragment -> {

                var drawable = if (Prefs.get().isPRofileComplete)
                    ContextCompat.getDrawable(this, R.drawable.bg_profile_toolbar_gradient)
                else
                    ColorDrawable(Color.parseColor("#FFFFFF"))

                supportActionBar!!.setBackgroundDrawable(drawable)

            }
            else -> {
                supportActionBar!!.setBackgroundDrawable(
                    ColorDrawable(Color.parseColor("#FFFFFF"))
                )
            }


        }

        if(checklistFragments.contains(destination.id)){
            highlightTabNone()
        }

        hideKeyboard()
*/
        if (fragmentsWhereBottomTabsShouldBeShown.contains(destination.id)) {
            showBack(false)
            bottom_navigation_bar.visibility = View.VISIBLE
        }
        else{
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
                DeviceRuntimePermission(
                    DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA,
                    null
                )
            )
        }
    }

    var fromChecklistToServiceDirectory = false
    override fun onBackPressed() {

        when {
            navController.currentDestination?.id == R.id.ServicesDirectoryFragment -> {
                if (fromChecklistToServiceDirectory) {
                    super.onBackPressed()
                } else
                    backToHome()
            }

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
            TAB_HOME ->
                highlightTab(tab_home, false)
            TAB_HEALTH ->
                highlightTab(tab_health, false)
            TAB_NOTIFICATION ->
                highlightTab(tab_notification, false)
            TAB_SETTINGS ->
                highlightTab(tab_settings, false)
            TAB_NONE ->
                highlightTab(null, true)
        }
    }

    private fun highlightTab(
        tvTab: TextView?,
        clear: Boolean
    ) {

        for (textView in arrTabTextView)
            setTvColor(textView, R.color.white)

        if (!clear) {
            setTvColor(tvTab, R.color.black_color)
        }

    }

    private fun getDestination(notification: NotificationBean?) {

        when (notification?.notificationtype) {
            NotificationType.OrderPlaced -> {
                navController.navigate(
                    R.id.ItemDetailFragment,
                    bundleOf(ParcelKeys.PK_SELL_ITEM_ID to notification?.id)
                )
            }
            NotificationType.OrderCancelled -> {
                navController.navigate(
                    R.id.ItemDetailFragment,
                    bundleOf(ParcelKeys.PK_SELL_ITEM_ID to notification?.id)
                )
            }
            NotificationType.ItemDelivered -> {
                navController.navigate(
                    R.id.MyOrderDetailFragment,
                    bundleOf("id" to notification?.id)
                )
            }
            NotificationType.ChecklistUpdate -> {
                navController.navigate(
                    R.id.ChecklistDetailFragment,
                    bundleOf("id" to notification?.id)
                )
            }
            NotificationType.BoxAllotment -> {
                navController.navigate(R.id.RedeemFreeBoxFragment)
            }
            NotificationType.ChecklistComplete -> {
                navController.navigate(
                    R.id.ChecklistDetailFragment,
                    bundleOf("id" to notification?.id)
                )
            }
            NotificationType.BudgetConsumed -> {
                navController.navigate(
                    R.id.BudgetTrackerFragment,
                    bundleOf("id" to notification?.id)
                )
            }
            else -> {

            }
        }

    }

    override fun requestLogout() {
        Prefs.get().loginData?.user_name?.let {
            showAlertDialog(
                it,
                getString(R.string.msg_app_logout_confirmation),
                getString(R.string.yes),
                getString(R.string.no),
                DialogInterface.OnClickListener { _, which ->
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        mBaseViewModel.logOut().observe(MainBoardActivity@ this, Observer {
                            onLogOutSuccess()
                        })
                    }
                })
        }
    }

    private fun onLogOutSuccess() {

        Prefs.get().clear()
        startActivity(
            Intent(
                this,
                SplashActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        finish()
    }


}
