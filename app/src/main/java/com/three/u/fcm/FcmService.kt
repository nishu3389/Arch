package com.three.u.fcm

import android.app.*
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.demo.fcm.NotificationBean
import com.demo.fcm.NotificationType
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.three.u.R
import com.three.u.ui.activity.HomeActivity
import com.three.u.base.sendToServer
import com.three.u.util.Constant
import com.three.u.util.Prefs

// Dummy Notification linnk
// http://3uwebtest.projectstatus.in/TermsOfUse
class FcmService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("device token", token)
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
            fromJson(remoteMessage?.data?.get("custom_notification"), NotificationBean::class.java)
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
        notifyIntent.putExtra("type", notificationBean.notificationtype)
        notifyIntent.putExtra("bean", notificationBean)
        var notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        notificationBean?.let {
            val mBuilder = NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.logo_icon)
                .setContentTitle(notificationBean.title)
                .setContentText(notificationBean.body)
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

        when (notification?.notificationtype) {

            NotificationType.OrderPlaced -> {
                intent = Intent(Constant.ACTION_ORDER_PLACED)
            }
            NotificationType.OrderCancelled -> {
                intent = Intent(Constant.ACTION_ORDER_CANCELLED)
            }
            NotificationType.ItemDelivered -> {
                intent = Intent(Constant.ACTION_ITEM_DELIVERED)
            }
            NotificationType.ChecklistUpdate -> {
                intent = Intent(Constant.ACTION_CHECKLIST_UPDATE)
            }
            NotificationType.ChecklistComplete -> {
                intent = Intent(Constant.ACTION_CHECKLIST_UPDATE)
            }
             NotificationType.BudgetConsumed -> {
                intent = Intent(Constant.ACTION_BUDGET)
            }
            NotificationType.BoxAllotment -> {
                intent = Intent(Constant.ACTION_BOX_ALLOTMENT)
            }
            NotificationType.ConceirgeProcessed -> {
                intent = Intent(Constant.ACTION_CONCIERGE_PROCESSED)
            }
            else -> {
                intent = Intent(Constant.ACTION_NAVIGATION)
            }

        }

        intent.putExtra("bean", notification)
        sendBroadcast(intent)
    }


}


