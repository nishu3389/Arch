package com.three.u.util.location

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.IOException
import java.util.*

object LocationAddress {
    private const val TAG = "LocationAddress"
    fun getAddressFromLocation(
        latitude: Double, longitude: Double,
        context: Context?, handler: Handler?
    ) {
        val thread: Thread = object : Thread() {
            override fun run() {
                val geocoder = Geocoder(context, Locale.getDefault())
                var result: String? = null
                var suburb: String? = null
                var postCode: String? = null
                var state: String? = null
                try {
                    val addressList =
                        geocoder.getFromLocation(
                            latitude, longitude, 1
                        )
                    if (addressList != null && addressList.size > 0) {
                        val address = addressList[0]
                        val sb = StringBuilder()
                        for (i in 0 until address.maxAddressLineIndex) {
                            sb.append(address.getAddressLine(i)).append("\n")
                        }
                        suburb = address.subAdminArea
                        postCode = address.postalCode
                        state = address.adminArea
                        //                        sb.append(address.getLocality() + ", " + address.getSubAdminArea() + ", " + address.getAdminArea()address.getFeatureName());
/* sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());*/result = sb.toString()
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Unable connect to Geocoder", e)
                } finally {
                    val message = Message.obtain()
                    message.target = handler
                    if (result != null) {
                        message.what = 1
                        val bundle = Bundle()
                        bundle.putString("address", result)
                        bundle.putString("suburb", suburb)
                        bundle.putString("state", state)
                        bundle.putString("postCode", result.substring(result.lastIndexOf(" ") + 1))
                        message.data = bundle
                    } else {
                        message.what = 1
                        val bundle = Bundle()
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n Unable to get address for this lat-long."
                        bundle.putString("address", result)
                        message.data = bundle
                    }
                    message.sendToTarget()
                }
            }
        }
        thread.start()
    }
}