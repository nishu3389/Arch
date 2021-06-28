package com.architecture.base

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.architecture.util.Util

class GlobalObservers {
    var successAlertLiveData = MutableLiveData<String>()
    var warningAlertLiveData = MutableLiveData<String>()
    var errorAlertLiveData = MutableLiveData<String>()
    var openInBrowserLiveData = MutableLiveData<String>()
    var callLiveData = MutableLiveData<String>()

    fun manageGlobalObservers(context: BaseActivity) {
        MainApplication.get().globalObservers.successAlertLiveData = MutableLiveData<String>()
        MainApplication.get().globalObservers.warningAlertLiveData = MutableLiveData<String>()
        MainApplication.get().globalObservers.errorAlertLiveData = MutableLiveData<String>()

        MainApplication.get().globalObservers.successAlertLiveData?.observe(context, Observer {
            it?.let {
                Util.showSuccessSneaker(context, it)
            }
        })

        MainApplication.get().globalObservers.warningAlertLiveData?.observe(context, Observer {
            it?.let {
                Util.showWarningSneaker(context, it)
            }
        })

        MainApplication.get().globalObservers.errorAlertLiveData?.observe(context, Observer {
            it?.let {
                Util.showErrorSneaker(context, it)
            }
        })

        MainApplication.get().globalObservers.openInBrowserLiveData?.observe(context, Observer {
            it?.let {
                val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(it))
                context.startActivity(intent)
            }
        })

        MainApplication.get().globalObservers.openInBrowserLiveData?.observe(context, Observer {
            it?.let {
                val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(it))
                context.startActivity(intent)
            }
        })

    }

}