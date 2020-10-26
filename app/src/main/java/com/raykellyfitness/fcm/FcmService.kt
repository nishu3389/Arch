package com.raykellyfitness.fcm

import android.app.*
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.demo.fcm.NotificationBean
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.raykellyfitness.R
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.base.sendToServer
import com.raykellyfitness.util.Constant
import com.raykellyfitness.util.Prefs

// Dummy Notification link
// http://3uwebtest.projectstatus.in/TermsOfUse
class FcmService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("device_token", token)
        Prefs.get().deviceToken = token
        token.sendToServer()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.e("message receive", "receive")
        if (Prefs.get().loginData != null) {
            createNotificationChannel(remoteMessage)
        }
    }

    private fun createNotificationChannel(remoteMessage: RemoteMessage?) {

        Log.e("message receive", "receive " + remoteMessage?.data)

        val notificationBean = Gson().run {
            fromJson(remoteMessage?.data?.get("body"), NotificationBean::class.java)
        }

        sendBroadCast(notificationBean)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.channel_id), name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }

        val notifyIntent = Intent(this, HomeActivity::class.java)
        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notifyIntent.putExtra(Constant.TYPE, notificationBean.notificationType)
        notifyIntent.putExtra(Constant.BEAN, notificationBean)
        var notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        notificationBean?.let {
            val mBuilder = NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.logo_icon)
                .setContentTitle(notificationBean.message)
                .setContentText(notificationBean.message)
                .setContentIntent(notifyPendingIntent)
                .setAutoCancel(true)
                //.setLargeIcon(bmp)
                .setColor(ContextCompat.getColor(baseContext, R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()


            /* to remove notification after click*/
            mBuilder.flags = mBuilder.flags or Notification.FLAG_AUTO_CANCEL

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(System.currentTimeMillis().toInt(), mBuilder)
        }
    }

    private fun sendBroadCast(notification: NotificationBean?) {
        var intent: Intent? = null
        intent = Intent(Constant.ACTION_ORDER_PLACED)

/*
        when (notification?.notificationtype) {

            NotificationType.MealReceived -> {
                intent = Intent(Constant.ACTION_ORDER_PLACED)
            }
            NotificationType.TipsReceived -> {
                intent = Intent(Constant.ACTION_ORDER_CANCELLED)
            }
            NotificationType.ExerciseReceived -> {
                intent = Intent(Constant.ACTION_ITEM_DELIVERED)
            }
            NotificationType.MotivationReceived -> {
                intent = Intent(Constant.ACTION_CHECKLIST_UPDATE)
            }
            NotificationType.BlogReceived -> {
                intent = Intent(Constant.ACTION_CHECKLIST_UPDATE)
            }
            else -> {
                intent = Intent(Constant.ACTION_NAVIGATION)
            }
        }
*/

        intent.putExtra("bean", notification)
        sendBroadcast(intent)
    }


}


