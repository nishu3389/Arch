package com.architecture.util.permission

import android.Manifest

/**
 * Created by Mohit Sharma on 24-10-2018.
 */
class DeviceRuntimePermission(
    val requestCode: Int,
    permissionArray: Array<String?>?
) {
    lateinit var permission: Array<String>
    private fun getPermissions(requestCode: Int): Array<String> {
        when (requestCode) {
            REQUEST_PERMISSION_LOCATION -> return arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            REQUEST_PERMISSION_EXTERNAL_STORAGE -> return arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            REQUEST_PERMISSION_CAMERA -> return arrayOf(
                Manifest.permission.CAMERA
            )
            REQUEST_CONTACT_PERMISSION -> return arrayOf(
                Manifest.permission.READ_CONTACTS
            )
            REQUEST_CAMERA_N_STORAGE_PERMISSION -> return arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            REQUEST_AUDIO_N_STORAGE_PERMISSION -> return arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION -> return arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> return arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
            )
        }
        return arrayOf()
    }

    companion object {
        const val REQUEST_PERMISSION_LOCATION = 301
        const val REQUEST_PERMISSION_EXTERNAL_STORAGE = 302
        const val REQUEST_PERMISSION_CAMERA = 303
        const val REQUEST_CONTACT_PERMISSION = 304
        const val REQUEST_CAMERA_N_STORAGE_PERMISSION = 305
        const val REQUEST_AUDIO_N_STORAGE_PERMISSION = 306
        const val REQUEST_ACCESS_COARSE__FINE_LOCATION_PERMISSION = 307
        const val REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA = 308
    }

    init {
        if (permissionArray == null) {
            permission = getPermissions(requestCode)
        }
    }
}