package com.raykellyfitness.util.location

import com.google.android.gms.maps.model.LatLng

/**
 * Created by admin on 07-12-2016.
 */
interface ISetLocality {
    fun setCurrentAddress(
        address: String?,
        latLng: LatLng?,
        suburb: String?,
        state: String?,
        postCode: String?
    )
}