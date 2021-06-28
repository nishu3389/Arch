package com.architecture.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.architecture.util.Util
import java.lang.reflect.Constructor

class MyViewModelProvider (val callback : AsyncViewController) : ViewModelProvider.NewInstanceFactory(){

    var extras : Bundle? = null

    constructor(callback : AsyncViewController, extras : Bundle?) : this(callback){
        this.extras = extras
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
             if (extras==null) {
                 val constructor: Constructor<T> = modelClass.getDeclaredConstructor(AsyncViewController::class.java)
                 return constructor.newInstance(callback)
            }else {
                 val constructor: Constructor<T> = modelClass.getDeclaredConstructor(AsyncViewController::class.java, Bundle::class.java)
                 return constructor.newInstance(callback, extras)
            }
        } catch (e: Exception) {
            Util.log("Could not create new instance of class %s" + modelClass.canonicalName)
            throw e
        }
    }

}