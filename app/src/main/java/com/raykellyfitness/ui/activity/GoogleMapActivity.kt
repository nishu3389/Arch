package com.raykellyfitness.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.raykellyfitness.R
import com.raykellyfitness.base.BaseActivity
import com.raykellyfitness.base.MainApplication
import com.raykellyfitness.base.isEmptyy
import com.raykellyfitness.databinding.ActivityGoogleMapBinding

import com.raykellyfitness.util.ParcelKeys
import com.raykellyfitness.util.location.GPSTracker
import com.raykellyfitness.util.permission.DeviceRuntimePermission
import com.raykellyfitness.util.permission.IPermissionGranted
import kotlinx.android.synthetic.main.activity_google_map.*
import java.util.*

class GoogleMapActivity: BaseActivity(), IPermissionGranted, OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener {

    private lateinit var mBinding: ActivityGoogleMapBinding
    private var googleMap: GoogleMap? = null
    private var latLng: LatLng? = null
    private var marker: Marker? = null
    private var gpsTracker: GPSTracker? = null
    private var selectAddress: String? = null
    private var selectLatitude = 0.0
    private var selectLongitude = 0.0
    private var userAddress:String?=""
    private var userLatitude:Double?=0.0
    private var userLongitude:Double?=0.0
    private var isLocationEditable:Boolean = false
    var placesClient: PlacesClient? = null
    var PLACE_PICKER_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        if(intent.extras!=null)
        {
            isLocationEditable=intent.getBooleanExtra(ParcelKeys.PK_LOCATION_EDITABLE,false);
            userAddress=intent.getStringExtra(ParcelKeys.PK_SELECTED_ADDRESS);
            userLatitude=intent.getDoubleExtra(ParcelKeys.PK_SELECTED_LATITUDE,0.0);
            userLongitude=intent.getDoubleExtra(ParcelKeys.PK_SELECTED_LONGITUDE,0.0);

        }


        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_google_map)
        MainApplication.setInstance(this)
        mBinding.clickHandler=DemoMapClickHandler()
        setSupportActionBar(mBinding.toolbar)
        mBinding.tvTitle.text = resources?.getString(R.string.select_location)
        if(isLocationEditable)
            mBinding.btnChange.visibility=View.VISIBLE
        else
            mBinding.btnChange.visibility=View.GONE

        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayShowTitleEnabled(false);
        setPermissionGranted(this)
        permissionDenied(DeviceRuntimePermission.REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            android.R.id.home->{
                finish()
            }
        }
        return  true
    }

    override fun requestLogout() {

    }

    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {

            DeviceRuntimePermission.REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION -> {
                gpsTracker = this?.let { GPSTracker(it) }
                initMap()
            }
        }
    }
    private fun initMap() {
        try {
            MapsInitializer.initialize(this)
        } catch (e: Exception) {
            Log.e("Address Map", "Could not initialize google play", e)
        }
        val mapFragment = this.supportFragmentManager
            .findFragmentById(R.id.gmap) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }
    override fun permissionDenied(requestCode: Int) {
        when (requestCode) {

            DeviceRuntimePermission.REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION -> (this as BaseActivity).checkAndAskPermission(
                DeviceRuntimePermission(
                    DeviceRuntimePermission.REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION,
                    null
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap?) {
        //        if(googleMap !=null) {
        googleMap = map
        googleMap!!.isMyLocationEnabled = true
        // mActivity.showProgress();
        if (latLng == null) getCurrentLatLng(false)

        Handler().postDelayed({
            if (googleMap != null) {
                googleMap!!.clear()
                if (marker != null) marker!!.remove()
                if (latLng != null) {
                    marker = googleMap!!.addMarker(
                        MarkerOptions()
                            .position(latLng!!)
                            .draggable(false)
                    )
                    mBinding.tvAddress.text = selectAddress
                }
                googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                googleMap!!.setOnCameraMoveListener(this)
                googleMap!!.setOnCameraIdleListener(this)
            }
        }, 2000)

    }

    private fun getCurrentLatLng(isAutoComplete: Boolean): LatLng?
    {
        val Latitude: Double
        val Longitude: Double
        Latitude = gpsTracker!!.latitude
        Longitude = gpsTracker!!.longitude

        if(!userAddress.isEmptyy())
        {
            selectLongitude = this!!.userLongitude!!
            selectLatitude = this!!.userLatitude!!
            selectAddress = userAddress
            latLng = LatLng(this!!.userLatitude!!, this!!.userLongitude!!)
        }
        else
        {
            selectLongitude = gpsTracker!!.longitude
            selectLatitude = gpsTracker!!.latitude
            selectAddress = getCompleteAddressString(gpsTracker!!.latitude, gpsTracker!!.longitude)
            latLng = LatLng(Latitude, Longitude)
        }



        if (latLng != null && googleMap != null) {
            val yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 0f)
            googleMap!!.animateCamera(yourLocation)
            if (marker != null) marker!!.remove()
            marker = googleMap!!.addMarker(
                MarkerOptions()
                    .position(latLng!!)
            )
        }
        hideProgressDialog()
        return LatLng(gpsTracker!!.getLatitude(), gpsTracker!!.getLongitude())
    }
    @SuppressLint("LongLogTag")
    private fun getCompleteAddressString(
        LATITUDE: Double,
        LONGITUDE: Double
    ): String? {
        var strAdd = ""
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses =
                geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
                Log.w("My Current loction address", strReturnedAddress.toString())
            } else {
                Log.w("My Current loction address", "No Address returned!")
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.w("My Current loction address", "Canont get Address!")
        }
        return strAdd
    }
    override fun onCameraMove() {
        googleMap!!.clear()
        // display imageView
        // display imageView
        mBinding.imgLocationPinUp.visibility = View.VISIBLE
    }

    override fun onCameraIdle() {
        mBinding.imgLocationPinUp.visibility = View.VISIBLE
        Handler().postDelayed({
            val markerOptions =
                MarkerOptions().position(googleMap!!.cameraPosition.target)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin))
            //googleMap.addMarker(markerOptions);
            selectLatitude = googleMap!!.cameraPosition.target.latitude
            selectLongitude = googleMap!!.cameraPosition.target.longitude
            selectAddress = getCompleteAddressString(selectLatitude, selectLongitude)
            mBinding.tvAddress.text = selectAddress
        }, 0)
    }
    inner  class DemoMapClickHandler()
    {
        fun doneClicked()
        {

            val intent = Intent()
            intent.putExtra(ParcelKeys.PK_SELECTED_LATITUDE, selectLatitude)
            intent.putExtra(ParcelKeys.PK_SELECTED_LONGITUDE, selectLongitude)
            intent.putExtra(ParcelKeys.PK_SELECTED_ADDRESS, selectAddress)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        fun changeClicked()
        {
            if (!Places.isInitialized()) {
                Places.initialize(applicationContext,getString(R.string.google_key))
            }
            // Set the fields to specify which types of place data to return.
            // Set the fields to specify which types of place data to return.
            val fields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS_COMPONENTS,
                Place.Field.ADDRESS
            )
            // Start the autocomplete intent.
            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields
            ).build(this@GoogleMapActivity.applicationContext)
            startActivityForResult(intent, PLACE_PICKER_REQUEST)
        }
    }

    fun closeKeyboard() {

        edt.requestFocus()

        Handler().postDelayed({
            val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(edt.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        },1000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        closeKeyboard()

        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                setPlaceData(place)
            }

            try {
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

        }

    }

    private fun setPlaceData(place: Place) {
        selectAddress = place.address
        selectLatitude = place.latLng!!.latitude
        selectLongitude = place.latLng!!.longitude

        mBinding.tvAddress.text = selectAddress

        latLng = place.latLng
        if (googleMap != null) {
            val yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 18f)
            googleMap!!.animateCamera(yourLocation)
            if (marker != null) marker!!.remove()
            marker = googleMap!!.addMarker(
                MarkerOptions()
                    .position(latLng!!)
            )
            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)));
        }
    }
    /*override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                iGoogleMapPresenter.setPlaceData(place)
                hideKeyboard()
            }
            try {
                hideKeyboard()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }*/

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}