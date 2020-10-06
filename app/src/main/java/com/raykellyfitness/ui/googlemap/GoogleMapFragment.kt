package com.raykellyfitness.ui.googlemap

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.net.PlacesClient
import com.raykellyfitness.R
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.base.BaseActivity
import com.raykellyfitness.base.BaseFragment
import com.raykellyfitness.base.titleWithLogo
import com.raykellyfitness.databinding .FragmentGoogleMapBinding
import com.raykellyfitness.model.response.ResponseItemDetail
import com.raykellyfitness.util.ParcelKeys
import com.raykellyfitness.util.ParcelKeys.PK_SELECTED_ADDRESS
import com.raykellyfitness.util.ParcelKeys.PK_SELECTED_LATITUDE
import com.raykellyfitness.util.ParcelKeys.PK_SELECTED_LONGITUDE
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.util.location.GPSTracker
import com.raykellyfitness.util.permission.DeviceRuntimePermission
import com.raykellyfitness.util.permission.IPermissionGranted
import java.util.*

class GoogleMapFragment : BaseFragment(), IPermissionGranted, OnMapReadyCallback,
    OnCameraMoveListener,
    OnCameraIdleListener
{
    private lateinit var mBinding:FragmentGoogleMapBinding ;
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
    var placesClient: PlacesClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getParcelAndConsume(R.id.GoogleMapFragment) {
            if (it.size() == 0) return@getParcelAndConsume
            var responseItemDetail=it.getSerializable(ParcelKeys.PK_LOCATION_DETAIL) as ResponseItemDetail
            if (responseItemDetail != null)
            {
                userAddress=responseItemDetail.locationAddress
                userLatitude= responseItemDetail.locationLatitude!!
                userLongitude= responseItemDetail.locationLongitude!!
            }
        }

        mBinding=FragmentGoogleMapBinding.inflate(layoutInflater,container,false).apply {
            clickHandler=GoogleMapClickHandler()
        }

        (activity as HomeActivity).title = context?.titleWithLogo(R.string.select_location)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).setPermissionGranted(this)
        permissionDenied(DeviceRuntimePermission.REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION)

    }


    inner class GoogleMapClickHandler {

        fun doneClicked()
        {
            Prefs.get().selectLocation=selectAddress.toString()
            putParcel(bundleOf(PK_SELECTED_ADDRESS to selectAddress,
                PK_SELECTED_LATITUDE to selectLatitude,
                PK_SELECTED_LONGITUDE to selectLongitude),R.id.AddItemFragment)
            findNavController().popBackStack()
        }
        fun changeClicked()
        {

        }
    }

    override fun permissionGranted(requestCode: Int)
    {
        when (requestCode) {

            DeviceRuntimePermission.REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION -> {
                gpsTracker = activity?.let { GPSTracker(it) }
                initMap()
            }
        }

    }

    private fun initMap() {
        try {
            MapsInitializer.initialize(activity)
        } catch (e: Exception) {
            Log.e("Address Map", "Could not initialize google play", e)
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.gmap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun permissionDenied(requestCode: Int)
    {
        when (requestCode) {

            DeviceRuntimePermission.REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION -> (activity as BaseActivity).checkAndAskPermission(
                DeviceRuntimePermission(
                    DeviceRuntimePermission.REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION,
                    null
                )
            )
        }
    }

    override fun onMapReady(map: GoogleMap?)
    {
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
                    mBinding.tvAddress.setText(selectAddress)
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

        if(userAddress!!.isNotEmpty())
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
       hideProgress()
        return LatLng(gpsTracker!!.getLatitude(), gpsTracker!!.getLongitude())
    }
    @SuppressLint("LongLogTag")
    private fun getCompleteAddressString(
        LATITUDE: Double,
        LONGITUDE: Double
    ): String? {
        var strAdd = ""
        val geocoder = Geocoder(activity, Locale.getDefault())
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
}