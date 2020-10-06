package com.raykellyfitness.util.location

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Handler
import android.os.Message
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.maps.model.LatLng

/**
 * Created by admin on 07-12-2016.
 */
class Locality {
    private var mActivity: Activity? = null
    private var iSetLocality: ISetLocality? = null
    private var latitude = 0.0
    private var longitude = 0.0
    private val gps: GPSTracker? = null
    fun currentAddress(mActivity: Activity?, iSetLocality: ISetLocality?) {
        this.mActivity = mActivity
        this.iSetLocality = iSetLocality
        val appLocationService = AppLocationService(
            mActivity!!
        )
        val location = lastKnownLocation
        if (location != null) {
            latitude = location.latitude
            longitude = location.longitude
            LocationAddress.getAddressFromLocation(
                latitude, longitude,
                mActivity, GeocoderHandler()
            )
        } else {
        }
    }

    /* LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {

                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }*/
    private val lastKnownLocation: Location?
        private get() =/* LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {

                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }*/
            null

    fun showSettingsAlert() {
        val alertDialog =
            AlertDialog.Builder(
                mActivity!!
            )
        alertDialog.setTitle("SETTINGS")
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?")
        alertDialog.setPositiveButton(
            "Settings"
        ) { dialog, which ->
            val intent = Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS
            )
            mActivity!!.startActivity(intent)
        }
        alertDialog.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }
        alertDialog.show()
    }

    private inner class GeocoderHandler : Handler() {
        override fun handleMessage(message: Message) {
            val locationAddress: String?
            val suburb: String?
            val state: String?
            val postCode: String?
            when (message.what) {
                1 -> {
                    val bundle = message.data
                    locationAddress = bundle.getString("address")
                    suburb = bundle.getString("suburb")
                    state = bundle.getString("state")
                    postCode = bundle.getString("postCode")
                }
                else -> {
                    locationAddress = null
                    suburb = null
                    state = null
                    postCode = null
                }
            }
            iSetLocality!!.setCurrentAddress(
                locationAddress,
                LatLng(latitude, longitude),
                suburb,
                state,
                postCode
            )
        }
    }
}