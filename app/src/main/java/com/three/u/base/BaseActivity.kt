package com.three.u.base

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import com.three.u.R
import com.three.u.databinding.AppCustomDialogBinding
import com.three.u.databinding.LayoutToolbarBinding
import com.three.u.ui.splash.SplashActivity
import com.three.u.util.*
import com.three.u.util.Fusedlocation.LocationManager
import com.three.u.util.permission.DeviceRuntimePermission
import com.three.u.util.permission.IPermissionGranted
import java.util.*

abstract class BaseActivity : AnotherBaseActivity(), CommonCallbacks, OnLocaleChangedListener {

    var mediaPlayerTap: MediaPlayer? = null
    lateinit var mBaseViewModel: BaseActivityViewModel
    private var aD: AlertDialog? = null
    private val localizationDelegate = LocalizationActivityDelegate(this)
    var isSecond = false

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        localizationDelegate.addOnLocaleChangedListener(this)
        localizationDelegate.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setupBasics()
        setStatusBarColor("#F5333F")
    }

    fun setStatusBarColor(color : String) {
        Util.updateStatusBarColor(color,this as FragmentActivity)
    }

    private fun setupBasics() {
        mBaseViewModel = ViewModelProviders.of(this, MyViewModelProvider(this as AsyncViewController)).get(BaseActivityViewModel::class.java)
        setObservers()
    }

    private fun showProgress() {
        MyProgress.show(this)
    }

    private fun hideProgress() {
        MyProgress.hide(this)
    }

    override fun showProgressDialog() {
        if (mBaseViewModel.progressDialogStatus.value == null || !mBaseViewModel.progressDialogStatus.value.equals("_show")) {
            mBaseViewModel.progressDialogStatus.value = "_show"
        }
    }

    override fun hideProgressDialog() {
        if (mBaseViewModel.progressDialogStatus.value == null || !mBaseViewModel.progressDialogStatus.value.equals("_hide")) {
            mBaseViewModel.progressDialogStatus.value = "_hide"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val bf = getCurrentFragment(BaseFragment::class.java)
        if (bf?.handlingBackPress() == false) {
            showAppCloseDialog()
        }
    }

    fun closeActivity() {
        finish()
    }

    private fun setObservers() {
        mBaseViewModel.keyboardController.observe(this, Observer {
            runOnUiThread {
                if (it) {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                } else {
                    val imm =
                        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                }
            }
        })

        mBaseViewModel.progressDialogStatus.observe(this, Observer {
            it?.let {
                if (it == "_show") {
                    showProgress()
                } else if (it == "_hide") {
                    hideProgress()
                    Log.d("NFCT", "Dialog Hiding")
                }
            }
        })

        mBaseViewModel.alertDialogController.observe(this, Observer {
            it?.let {
                if (aD?.isShowing == true) aD?.dismiss()

                val builder = AlertDialog.Builder(this).setCancelable(false)

                val dialogBinding =
                    AppCustomDialogBinding.inflate(layoutInflater, null, false).apply {
                        mBaseViewModel.alertDialogSpecs.message = it
                        specs = mBaseViewModel.alertDialogSpecs
                    }
                builder.setView(dialogBinding.root)
                aD = builder.create()
                aD!!.window?.setBackgroundDrawableResource(R.drawable.bg_white_updates)

                dialogBinding.btnYes.push()?.setOnClickListener {
                    mBaseViewModel.alertDialogSpecs.alertDialogBtnListener?.onClick(
                        aD,
                        DialogInterface.BUTTON_POSITIVE
                    )
                    aD?.dismiss()
                }
                dialogBinding.btnNo.push()?.setOnClickListener {
                    mBaseViewModel.alertDialogSpecs.alertDialogBtnListener?.onClick(
                        aD,
                        DialogInterface.BUTTON_NEGATIVE
                    )
                    aD?.dismiss()
                }


                aD!!.show()
                var button : Button
                button= aD?.getButton(DialogInterface.BUTTON_POSITIVE)!!

                button?.setBackgroundResource(R.color.white)
                mBaseViewModel.alertDialogController.value = null
            }

        })

       /* mBaseViewModel.responseLogOut?.observe(this, Observer {
           *//* if (it.responseCode == 200) {
                onLogOutSuccess()
            }*//*
        })*/
    }

    override fun setupToolBar(toolbarBinding: LayoutToolbarBinding, showBack: Boolean, title: String?) {

        setSupportActionBar(toolbarBinding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (title?.isNotEmpty() == true) {
            toolbarBinding.tvTitle.text = title
        } else {
            toolbarBinding.tvTitle.text = ""
        }

        if (showBack) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbarBinding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
            toolbarBinding.toolbar.setNavigationIcon(R.drawable.menu_icon)
        }

    }

    override fun getSharedModel(): BaseActivityViewModel {
        return mBaseViewModel
    }

    override fun showAlertDialog(msg: String, btnListener: DialogInterface.OnClickListener?) {
        mBaseViewModel.alertDialogSpecs = AlertDialogSpecs()
        mBaseViewModel.alertDialogSpecs.alertDialogBtnListener = btnListener
        mBaseViewModel.alertDialogController.value =  if(!msg.isEmptyy()) msg.trim() else getString(R.string.something_went_wrong)
    }

    override fun showAlertDialog(title: String, msg: String, btnPosTxt: String, btnNegTxt: String, btnListener: DialogInterface.OnClickListener?) {
        mBaseViewModel.alertDialogSpecs = AlertDialogSpecs()
        mBaseViewModel.alertDialogSpecs.title = title
        mBaseViewModel.alertDialogSpecs.btnPos = btnPosTxt
        mBaseViewModel.alertDialogSpecs.btnNeg = btnNegTxt
        mBaseViewModel.alertDialogSpecs.alertDialogBtnListener = btnListener
        mBaseViewModel.alertDialogController.value = msg
    }

    override fun hideAlertDialog() {
        mBaseViewModel.alertDialogController.value = "null"
    }

    override fun hideKeyboard() {
        mBaseViewModel.keyboardController.value = false
    }

    override fun hideKeyboard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun showKeyboard() {
        mBaseViewModel.keyboardController.value = true
    }

    override fun onNoInternet() {
        showAlertDialog(getString(R.string.no_network_connected), null)
    }

    override fun onApiCallFailed(apiUrl: String, errCode: Int, errorMessage: String): Boolean {
        if (errCode == 202) {
            showAlertDialog("Your session has been expired. Please login now.", DialogInterface.OnClickListener { _, _ ->
                onLogOutSuccess()
            })
        }
        //Give fragment a chance to handle api call failure
        else if (getCurrentFragment(BaseFragment::class.java)?.onApiRequestFailed(apiUrl, errCode, errorMessage) == false) {
            showAlertDialog(errorMessage, null)
            //fragment has nothing to do with this failure, can put some logic here
        }
        return true
    }

    private fun onLogOutSuccess() {

        Prefs.get().clear()
        startActivity(Intent(this, SplashActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        finish()
    }

    open fun onFilePicked(pickedFileUri: String) {
        getCurrentFragment(BaseFragment::class.java)?.onFilePicked(pickedFileUri)
    }

    override fun setupActionBarWithNavController(toolbar: Toolbar) {}

    override fun forceBack() {
        super.onBackPressed()
    }

    private fun showAppCloseDialog() {
        if (!isSecond) {
            isSecond = true
        } else {
            finish()
        }
    }


    override fun isConnectedToNetwork(): Boolean {
        val cm = MainApplication.get().getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null

    }

    fun onReceiveLocation(newLocation: Location?) {
        val f = getCurrentFragment(BaseFragment::class.java)
        if (f?.onReceiveLocation(newLocation) == false) {

        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    override fun onResume() {
        super.onResume()
        localizationDelegate.onResume(this)
    }

    override fun getResources(): Resources? {
        return localizationDelegate.getResources(super.getResources())
    }

    // Just override method locale change event
    override fun onBeforeLocaleChanged() {}

    override fun onAfterLocaleChanged() {}

    //permission
     var iPermissionGranted: IPermissionGranted? = null
    open fun setPermissionGranted(iPermissionGranted: IPermissionGranted?) {
        this.iPermissionGranted = iPermissionGranted
    }

    open fun checkAndAskPermission(runtimePermission: DeviceRuntimePermission): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (iPermissionGranted != null) iPermissionGranted!!.permissionGranted(runtimePermission.requestCode)
            return true
        }
        val results = ArrayList<String>()
        for (s in runtimePermission.permission) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    s
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                results.add(s)
            }
        }
        return if (results.size == 0) {
            if (iPermissionGranted != null) iPermissionGranted!!.permissionGranted(runtimePermission.requestCode)
            true
        } else {
            val arr = arrayOfNulls<String>(results.size)
            requestPermission(results.toArray(arr), runtimePermission.requestCode)
            false
        }
    }

    open fun requestPermission(perms: Array<String?>?, requestCode: Int) {
        ActivityCompat.requestPermissions(this, perms!!, requestCode)
    }

    private  fun getPermissionDialogContent(permission: String): String? {
        var permTitle = ""
        var descKey = ""
        when (permission) {
            Manifest.permission.CAMERA -> {
                permTitle = "Camera Permission"
                descKey = "to capture photos from the camera."
            }
            Manifest.permission.ACCESS_COARSE_LOCATION -> {
                permTitle = "Location Permission"
                descKey = "to get your location for google map."
            }
            Manifest.permission.ACCESS_FINE_LOCATION -> {
                permTitle = "Location Permission"
                descKey = "to get your location for google map."
            }
            Manifest.permission.READ_EXTERNAL_STORAGE -> {
                permTitle = "Storage Access Permission"
                descKey = "to pick an image from your phone."
            }
        }
        return getString(R.string.msg_permission_justification, permTitle, descKey)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val grantedPermissions =
            ArrayList<String>()
        var i = 0
        val len = permissions.size
        while (i < len) {
            val permission = permissions[i]
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                val showRationale = shouldShowRequestPermissionRationale(permission)
                if (!showRationale) {
                    if (permission != Manifest.permission.ACCESS_COARSE_LOCATION) {
                        getPermissionDialogContent(permission)?.let {
                            showRequestPermissionAlert(
                                "Permission Required !!", it,
                                true, permission, requestCode
                            )
                        }
                    }
                    isShowRequestAlert = false

                } else if (permission != Manifest.permission.ACCESS_COARSE_LOCATION) {
                    getPermissionDialogContent(permission)?.let {
                        showRequestPermissionAlert(
                            "Permission Required !!", it,
                            false, permission, requestCode
                        )
                    }
                }
                // user denied WITHOUT never ask again
// this is a good place to explain the user
// why you need the permission and ask if he want
// to accept it (the rationale)
            } else if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(permission)
            }
            i++
        }
        if (grantedPermissions.size == permissions.size && permissions.size != 0) {
            if (iPermissionGranted != null) iPermissionGranted!!.permissionGranted(requestCode)
        }
    }

    private var isShowRequestAlert = false
    private  fun showRequestPermissionAlert(title: String, message: String, isNeverAskAgainChecked: Boolean, permission: String, reqCode: Int) {
        var btnText = ""
        btnText = if (isNeverAskAgainChecked) {
            "Go to Settings"
        } else {
            "Ask again"
        }
        val builder =
            android.app.AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            btnText
        ) { dialog: DialogInterface?, which: Int ->
            if (isNeverAskAgainChecked) {
                isShowRequestAlert = true
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri =
                    Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, ParcelKeys.REQUEST_ENABLE_PERMISSION_SETTINGS)
            } else {
                requestPermission(arrayOf(permission), reqCode)
            }
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, which: Int ->
            isShowRequestAlert = true
            dialog.dismiss()
        }
        builder.show()
    }

    fun playTap() {
        try {
            if (mediaPlayerTap == null) {
                val resID = resources!!.getIdentifier("pop", "raw", packageName)
                mediaPlayerTap = MediaPlayer.create(this, resID)
                mediaPlayerTap!!.isLooping = false
            }

            if (mediaPlayerTap != null && !mediaPlayerTap!!.isPlaying)
                mediaPlayerTap!!.start()

        } catch (e: Exception) {
        }
    }

    fun stopTap() {
        try {
            if (mediaPlayerTap != null && mediaPlayerTap!!.isPlaying)
                mediaPlayerTap!!.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}