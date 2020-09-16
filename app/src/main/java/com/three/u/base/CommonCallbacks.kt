package com.three.u.base

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.three.u.databinding.LayoutToolbarBinding


interface CommonCallbacks : AsyncViewController {

    fun setupToolBar(toolbarBinding: LayoutToolbarBinding, showBack: Boolean, title: String?)

    fun setupActionBarWithNavController(toolbar: Toolbar)

    fun hideKeyboard(v: View)

    //fun requestFilePicker(actionType : Int)

    fun forceBack()

    fun isConnectedToNetwork(): Boolean

    fun requestLogout()

    fun getSharedModel() : BaseActivityViewModel

    //fun requestLocation()

}