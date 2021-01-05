package com.raykellyfitness.util.location

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import androidx.core.content.ContextCompat

class AppLocationService(context: Context) : Service(),
    LocationListener {
    protected var locationManager: LocationManager?
    var location: Location? = null
    private val context: Context
    fun getLocation(provider: String?): Location? {
        if (locationManager!!.isProviderEnabled(provider)) {
            locationManager!!.requestLocationUpdates(
                provider,
                MIN_TIME_FOR_UPDATE,
                MIN_DISTANCE_FOR_UPDATE.toFloat(),
                this
            )
            if (locationManager != null) {
                location = locationManager!!.getLastKnownLocation(provider)
                return location
            }
        }
        return null
    }

    override fun onLocationChanged(location: Location) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(
        provider: String,
        status: Int,
        extras: Bundle
    ) {
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    private val isShowRequestAlert = true
    fun checkLocationPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return result == PackageManager.PERMISSION_GRANTED
    } /*
    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

    }*/

    companion object {
        private const val MIN_DISTANCE_FOR_UPDATE: Long = 10
        private const val MIN_TIME_FOR_UPDATE = 1000 * 60 * 2.toLong()
        /**
         * Location Permission
         */
        const val LOCATION_PERMISSION_REQUEST_CODE = 100
        const val REQUEST_ENABLE_PERMISSION_SETTINGS = 400
    }

    init {
        locationManager = context
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        this.context = context
    }
}