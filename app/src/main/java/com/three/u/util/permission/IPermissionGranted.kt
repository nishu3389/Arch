package com.three.u.util.permission

/**
 * Created by Mohit Sharma on 4/19/2018.
 */
interface IPermissionGranted {
    fun permissionGranted(requestCode: Int)

    fun permissionDenied(requestCode: Int)
}
