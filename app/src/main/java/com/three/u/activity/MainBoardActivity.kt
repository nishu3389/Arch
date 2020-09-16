package com.three.u.activity

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.demo.fcm.NotificationBean
import com.demo.fcm.NotificationType
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import com.three.u.R
import com.three.u.base.*
import com.three.u.databinding.ActivityMainBinding
import com.three.u.splash.SplashActivity
import com.three.u.util.Constant
import com.three.u.util.Constant.CODE_DIRECTORY
import com.three.u.util.Constant.CODE_PACKAGING
import com.three.u.util.Constant.CODE_UTILITIES
import com.three.u.util.Constant.KEY_PROFILE_TYPE
import com.three.u.util.Constant.PROFILE_NORMAL
import com.three.u.util.ParcelKeys
import com.three.u.util.Prefs
import com.three.u.util.Validator
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_main.*

class MainBoardActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    NavController.OnDestinationChangedListener, IPermissionGranted {
    var popupShown = false
    var notificationModel : NotificationBean? = null
    var fromNotification : Boolean = false
    private var map = HashMap<AppCompatImageView, Int>()
    private var arrTabBump = ArrayList<ImageView>()
    private var arrTabTextView = ArrayList<TextView>()

    private val TAB_DIRECTORY = 1
    private val TAB_SCAN = 2
    private val TAB_CONCIERGE = 3
    private val TAB_PACKAGING = 4
    private val TAB_UTILITIES = 5
    private val TAB_NONE = 6
    private var CODE_RECENT_DIRECTORY_FRAGMENT: Int = 0

    lateinit var context: Context
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    private val topLevelFIds = listOf(
        R.id.HomeFragment,
        R.id.ProfileShowFragment,
        R.id.SalesProfileEditFragment,
        R.id.U3AgentFragment,
        R.id.AddCheckListFragment,
        R.id.SellOrGiveAwayFragment,
        R.id.MyCheckListFragment,
        R.id.ResidentialChecklistFragment,
        R.id.RedeemFreeBoxFragment,
        R.id.ServicesDirectoryFragment,
        R.id.ServiceDirDetailsFragment,
        R.id.ConciergeRequestFragment,
        R.id.ScanQRCodeFragment,
        R.id.ReferAFriendFragment,
        R.id.ContactUsFragment,
        R.id.MyOrdersFragment
    )

    private val fragmentsWhereBottomTabsShouldBeShown = listOf(
        R.id.HomeFragment,
        R.id.MyCheckListFragment,
        R.id.ServicesDirectoryFragment,
        R.id.ConciergeRequestFragment,
        R.id.ScanQRCodeFragment,
        R.id.ResidentialChecklistFragment,
        R.id.ChecklistDetailFragment,
        R.id.MasterChecklistFragment
    )

    private val checklistFragments = listOf(
        R.id.ResidentialChecklistFragment,
        R.id.ChecklistDetailFragment,
        R.id.MasterChecklistFragment
    )

    private val fragmentsTabs = listOf(
        R.id.ServicesDirectoryFragment,
        R.id.ScanQRCodeFragment,
        R.id.ConciergeRequestFragment
    )

    lateinit var mBinding: ActivityMainBinding

    var instance: MainBoardActivity? = null


   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initWork()
        otherWork()
        MainApplication.setInstance(this)
       lockDrawer()
    }


    var passwordVerified: Boolean = false

    private val INTENT_AUTHENTICATE: Int = 3487
    var keyguardManager: KeyguardManager? = null

    fun showLockScreen() {
        keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val km = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if (km.isKeyguardSecure) {
                startActivityForResult(
                    km.createConfirmDeviceCredentialIntent(
                        "3U Customer",
                        "Unlock your screen"
                    ), INTENT_AUTHENTICATE
                )
                Prefs.get().lastLockedMillis = System.currentTimeMillis()
            }
        }
    }

    private fun otherWork() {
        upadteNameImage()
        unlockDrawer()
        bottomNavigationWork()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.d(" lifecycle", "onNewIntent")
    }






    private fun initWork() {
        context = this
        intent?.let {
            if (intent != null && intent.extras != null && intent.extras!!.containsKey("bean")) {
                notificationModel= intent.getParcelableExtra<NotificationBean>("bean")
                fromNotification = true
            }
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.apply {
            toolbar.setTitle("")
            setSupportActionBar(toolbar)
        }
        mBinding.tvTitle.setText(resources?.getString(R.string.home))

        val drawerLayout: DrawerLayout = mBinding.drawerLayout
        navController = setNavigationController()
        if(fromNotification)
        getDestination(notificationModel)
    }


    /* override fun setLang(strLang: String) {

     }
 */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun bottomNavigationWork() {

        map = hashMapOf(
            img_utility to R.drawable.utilities_icon,
            img_directory to R.drawable.directory,
            img_scan to R.drawable.boxvert,
            img_concierge to R.drawable.concierge,
            img_packaging to R.drawable.packages
        )

        arrTabBump =
            arrayListOf(bump_directory, bump_scan, bump_concierge, bump_packaging, bump_utilities)
        arrTabTextView = arrayListOf(
            tv_tab_directory,
            tv_tab_scan,
            tv_tab_concierge,
            tv_tab_packaging,
            tv_tab_utilities
        )

        tab_directory.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.ServicesDirectoryFragment && CODE_RECENT_DIRECTORY_FRAGMENT == CODE_DIRECTORY)
                return@setOnClickListener

            fromChecklistToServiceDirectory = false
            CODE_RECENT_DIRECTORY_FRAGMENT = CODE_DIRECTORY
            navController.navigate(R.id.ServicesDirectoryFragment)
        }

        tab_scan.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.ScanQRCodeFragment)
                return@setOnClickListener
            setPermissionGranted(this)
            permissionDenied(DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA)
        }

        tab_concierge.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.ConciergeRequestFragment)
                return@setOnClickListener
            navController.navigate(R.id.ConciergeRequestFragment)
        }

        tab_packaging.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.ServicesDirectoryFragment && CODE_RECENT_DIRECTORY_FRAGMENT == CODE_PACKAGING)
                return@setOnClickListener

            fromChecklistToServiceDirectory = false
            CODE_RECENT_DIRECTORY_FRAGMENT = CODE_PACKAGING
            var bundle = Bundle()
            bundle.putInt("BusinessCategoryID", CODE_PACKAGING)
            navController.navigate(R.id.ServicesDirectoryFragment, bundle)
        }

        tab_utilities.pushTab()?.setOnClickListener {
            if (navController.currentDestination?.id == R.id.ServicesDirectoryFragment && CODE_RECENT_DIRECTORY_FRAGMENT == CODE_UTILITIES)
                return@setOnClickListener

            fromChecklistToServiceDirectory = false
            CODE_RECENT_DIRECTORY_FRAGMENT = CODE_UTILITIES
            var bundle = Bundle()
            bundle.putInt("BusinessCategoryID", CODE_UTILITIES)
            navController.navigate(R.id.ServicesDirectoryFragment, bundle)
        }


    }

    fun highlightTabNone(){
        highlightTab(TAB_NONE)
    }

    fun highlightDirectoryTab() {
        highlightTab(TAB_DIRECTORY)
    }

    fun highlightConciergeTab() {
        highlightTab(TAB_CONCIERGE)
    }

    fun highlightScanTab() {
        highlightTab(TAB_SCAN)
    }

    fun highlightPackagingTab() {
        highlightTab(TAB_PACKAGING)
    }

    fun highlightUtilitiesTab() {
        highlightTab(TAB_UTILITIES)
    }

    fun highlightBusinessMenuItem() {
        mBinding.navView.menu.findItem(R.id.BusinessChecklistFragment).isChecked = true
    }

    fun highlightResidentialMenuItem() {
        mBinding.navView.menu.findItem(R.id.ResidentialChecklistFragment).isChecked = true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.HomeFragment -> {

                if (navController.currentDestination?.id == R.id.HomeFragment) {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                    return true
                }

                navController.navigate(R.id.HomeFragment)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_NONE)

                return true
            }
            R.id.ProfileShowFragment -> {
                var bundle = bundleOf(KEY_PROFILE_TYPE to PROFILE_NORMAL)
                navController.navigate(R.id.ProfileShowFragment, bundle)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_NONE)

                return true
            }
            R.id.SalesProfileEditFragment -> {
                navController.navigate(R.id.SalesProfileEditFragment)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_NONE)

                return true
            }
            R.id.U3AgentFragment -> {
                navController.navigate(R.id.U3AgentFragment)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_NONE)

                return true
            }
            R.id.RedeemFreeBoxFragment -> {
                navController.navigate(R.id.RedeemFreeBoxFragment)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_NONE)

                return true
            }
            R.id.ReferAFragment -> {
                navController.navigate(R.id.ReferAFriendFragment)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_NONE)

                return true
            }
            R.id.ConciergeRequestFragment -> {
                if (navController.currentDestination?.id == R.id.ConciergeRequestFragment) {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                    return true
                }

                navController.navigate(R.id.ConciergeRequestFragment)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_CONCIERGE)
                return true
            }
            R.id.Logout -> {
                requestLogout()
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                return true
            }
            /* R.id.ChecklistFragment -> {
                 navController.navigate(R.id.MyCheckListFragment)
                 mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                 highlightTab(TAB_NONE)

                 return true
             }*/
            R.id.ResidentialChecklistFragment -> {
                navController.navigate(
                    R.id.ResidentialChecklistFragment,
                    bundleOf(Pair("from", true))
                )
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_NONE)

                return true
            }
            R.id.BusinessChecklistFragment -> {
                navController.navigate(
                    R.id.ResidentialChecklistFragment,
                    bundleOf(Pair("from", false))
                )
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_NONE)

                return true
            }
            R.id.SellGiveAwaFragment -> {
                navController.navigate(R.id.SellOrGiveAwayFragment)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                highlightTab(TAB_NONE)
                return true
            }
            R.id.ServicesDirectoryFragment -> {

                if (navController.currentDestination?.id == R.id.ServicesDirectoryFragment && CODE_RECENT_DIRECTORY_FRAGMENT == CODE_DIRECTORY) {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                    return true
                }

                navController.navigate(R.id.ServicesDirectoryFragment)
                CODE_RECENT_DIRECTORY_FRAGMENT = CODE_DIRECTORY
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)

                return true
            }
            R.id.ContactUsFragment -> {
                navController.navigate(R.id.ContactUsFragment)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)

                return true
            }
            R.id.SacnQRFragment -> {

                if (navController.currentDestination?.id == R.id.ScanQRCodeFragment) {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                    return true
                }

                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)
                setPermissionGranted(this)
                permissionDenied(DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA)
                return true
            }
            R.id.ThreeUMarketFargment -> {
                navController.navigate(R.id.BrowseItemListFragment)

                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)

                return true
            }
            R.id.MyOrdersFragment -> {
                navController.navigate(R.id.MyOrdersFragment)
                mBinding.drawerLayout.closeDrawer(GravityCompat.START, true)

                return true
            }
        }
        return false
    }

    private fun setNavigationController(): NavController {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener(this)
        mBinding.navView.setupWithNavController(navController)
        mBinding.navView.setNavigationItemSelectedListener(this)
        navController.addOnDestinationChangedListener(this)
        return navController
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
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
        if (topLevelFIds.contains(destination.id)) {
            // mBinding.relShadow.showOrGone(true)
            showDrawerMenu()
        } else {
            //  mBinding.relShadow.showOrGone(false)
            hideDrawerMenu()
        }

        if (fragmentsWhereBottomTabsShouldBeShown.contains(destination.id))
            bottom_navigation_bar.visibility = View.VISIBLE
        else
            bottom_navigation_bar.visibility = View.GONE

    }

    fun showBottomTabs() {
        bottom_navigation_bar.visibility = View.VISIBLE
    }

    fun hideBottomTabs() {
        bottom_navigation_bar.visibility = View.GONE
    }

    private fun hideDrawerMenu() {
        // bottom_navigation is BottomNavigationView
        with(mBinding.navView) {
            if (visibility == View.VISIBLE && alpha == 1f) {
                animate().alpha(0f).withEndAction { visibility = View.GONE }.duration = 200
            }
        }
    }

    private fun showDrawerMenu() {
        // bottom_navigation is BottomNavigationView
        with(mBinding.navView) {
            visibility = View.VISIBLE
            animate().alpha(1f).duration = 200
        }
    }

    fun lockDrawer() {
        mBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        mBinding.apply {

            toolbar.setNavigationIcon(resources!!.getDrawable(R.drawable.back_icon))
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }


    fun unlockDrawer() {
        mBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        mBinding.apply {
            toolbar.setNavigationIcon(resources!!.getDrawable(R.drawable.menu_icon))
            toolbar.setNavigationOnClickListener {
                mBinding.drawerLayout.openDrawer(Gravity.LEFT)
            }
        }
    }

    fun upadteNameImage() {
        var view: View
        view = mBinding.navView.getHeaderView(0)

        var imageView: ImageView
        var textView: TextView
        textView = view.findViewById(R.id.textView)
        imageView = view.findViewById(R.id.imageView)

        textView.setText(Prefs.get().loginData?.customerName)
        Picasso.get().load(Prefs.get().loginData?.logoProfilePic).into(imageView)

    }

    fun updatetopBarColor(isBlue: Boolean) {
        if (isBlue)
            mBinding.toolbar.setBackgroundColor(Color.parseColor("#A9EFFF"))
        else
            mBinding.toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }

    override fun setTitle(title: CharSequence?) {
        mBinding.tvTitle.setText(title)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        Log.d(" lifecycle", "onTrimMemory")
    }

    override fun onPause() {
        super.onPause()
        passwordVerified = false
    }

    override fun onResume() {
        super.onResume()
        if (!passwordVerified)
            showLockScreen()
        else {

        }
        Log.d(" lifecycle", "onResume")
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
                mBinding.navView.menu.findItem(R.id.SacnQRFragment).isChecked = true
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
                if(fromChecklistToServiceDirectory) {
                    super.onBackPressed()
                }
                else
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
            TAB_DIRECTORY -> {
                highlightTab(
                    R.drawable.directory_active,
                    img_directory,
                    tv_tab_directory,
                    bump_directory,
                    false
                )
                mBinding.navView.menu.findItem(R.id.ServicesDirectoryFragment).isChecked = true
            }
            TAB_SCAN -> {
                highlightTab(R.drawable.boxvert_active, img_scan, tv_tab_scan, bump_scan, false)
                mBinding.navView.menu.findItem(R.id.SacnQRFragment).isChecked = true
            }
            TAB_CONCIERGE -> {
                highlightTab(
                    R.drawable.concierge_active,
                    img_concierge,
                    tv_tab_concierge,
                    bump_concierge,
                    false
                )
                mBinding.navView.menu.findItem(R.id.ConciergeRequestFragment).isChecked = true
            }
            TAB_PACKAGING -> highlightTab(
                R.drawable.packages_active,
                img_packaging,
                tv_tab_packaging,
                bump_packaging,
                false
            )
            TAB_UTILITIES -> highlightTab(
                R.drawable.utilities_active,
                img_utility,
                tv_tab_utilities,
                bump_utilities,
                false
            )
            TAB_NONE -> highlightTab(null, null, null, null, true)
        }
    }

    private fun highlightTab(
        drawableActive: Int?,
        imageViewToHighlight: AppCompatImageView?,
        tvTab: TextView?,
        bump: ImageView?,
        clear: Boolean
    ) {

        for (arrTabIcon in map)
            setDrawable(arrTabIcon.key, arrTabIcon.value)

        for (view in arrTabBump)
            view.invisible()

        for (textView in arrTabTextView)
            setTvColor(textView, R.color.black)

        if (!clear) {
            setDrawable(imageViewToHighlight, drawableActive)
            bump.visible()
            setTvColor(tvTab, R.color.app_green)
//            playTap()
        }

    }

    private fun getDestination(notification: NotificationBean?) {

        when (notification?.notificationtype) {
            NotificationType.OrderPlaced -> {
                navController.navigate(R.id.ItemDetailFragment, bundleOf(ParcelKeys.PK_SELL_ITEM_ID to notification?.id))
            }
            NotificationType.OrderCancelled -> {
                navController.navigate(R.id.ItemDetailFragment, bundleOf(ParcelKeys.PK_SELL_ITEM_ID to notification?.id))
            }
            NotificationType.ItemDelivered -> {
                navController.navigate(R.id.MyOrderDetailFragment, bundleOf("id" to notification?.id))
            }
            NotificationType.ChecklistUpdate -> {
                navController.navigate(R.id.ChecklistDetailFragment, bundleOf("id" to notification?.id))
            }
            NotificationType.BoxAllotment -> {
                navController.navigate(R.id.RedeemFreeBoxFragment)
            }
            NotificationType.ChecklistComplete -> {
                navController.navigate(R.id.ChecklistDetailFragment, bundleOf("id" to notification?.id))
            }
            NotificationType.BudgetConsumed -> {
                navController.navigate(R.id.BudgetTrackerFragment, bundleOf("id" to notification?.id))
            }
            else -> {

            }
        }

    }

    override fun requestLogout() {
        Prefs.get().loginData?.customerName?.let {
            showAlertDialog(
                it,
                getString(R.string.msg_app_logout_confirmation),
                getString(R.string.yes),
                getString(R.string.no),
                DialogInterface.OnClickListener { _, which ->
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                         mBaseViewModel.logOut().observe(MainBoardActivity@this, Observer {
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
