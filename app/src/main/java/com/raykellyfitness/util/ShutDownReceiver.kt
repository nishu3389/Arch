package com.raykellyfitness.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.demo.fcm.NotificationBean
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.ui.activity.HomeActivity

class ShutDownReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        /*try {

            var event = "";
            if(Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
                event = "Great - Shutdown event recieved - FROM BroadcastReceiver"
                Prefs.get().SHUTDOWN = event
                vibrate(1000)
                // database operation
            }

            if(Intent.ACTION_AIRPLANE_MODE_CHANGED == intent.getAction()?.intern()) {
                // some operation
                event = "ACTION_AIRPLANE_MODE_CHANGED - FROM BroadcastReceiver"
                Prefs.get().SHUTDOWN = event
            }


            event.log()
            event.toast()
            event.sendNotification()
        }catch (e:Exception){}*/
    }

}
