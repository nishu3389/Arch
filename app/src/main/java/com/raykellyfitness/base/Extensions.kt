package com.raykellyfitness.base

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.binaryfork.spanny.Spanny
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.squareup.picasso.Picasso
import com.thekhaeng.pushdownanim.PushDownAnim
import com.raykellyfitness.R
import com.raykellyfitness.networking.Api.BASE_URL
import com.raykellyfitness.networking.Api.UPDATE_TOKEN_TO_SERVER
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.util.Prefs
import com.raykellyfitness.util.RoundedCornersTransform
import com.raykellyfitness.util.Util
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Returns a random element.
 */
fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null

fun View.find(id: Int): View {
    return this.findViewById(id)
}

fun View.get(): String {
    if ((this as EditText).getText().isEmpty())
        return ""
    else
        return (this as EditText).getText().toString()
}

fun EditText.get(): String {
    if (this.getText().isEmpty())
        return ""
    else
        return this.getText().toString().trim()
}

fun ImageView.set(imgUrl: String?) {
    if (imgUrl.isNullOrEmpty())
        Picasso.get().load(R.drawable.placeholder).into(this)
    else
        Picasso.get().load(imgUrl).placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
            .into(this)
}

fun ImageView.set(con: Context, imgUrl: String?) {
    if (imgUrl.isNullOrEmpty())
        Glide.with(con).load(R.drawable.placeholder).into(this)
    else
        Glide.with(con).load(imgUrl).placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder).into(this)
}

fun ImageView.set(con: Context, img: Int?) {
    if (img == 0)
        Glide.with(con).load(R.drawable.placeholder).into(this)
    else
        Glide.with(con).load(img).placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder).into(this)
}

fun ImageView.set(con: Context, imgUrl: String?, placeholder: Int) {
    if (imgUrl.isNullOrEmpty())
        Glide.with(con).load(placeholder).into(this)
    else
        Glide.with(con).load(imgUrl).placeholder(placeholder)
            .error(placeholder).into(this)
}

fun EditText.setGender(gender: Int?) {
    if (gender != null) {
        when (gender) {
            1 -> this.setText("Male")
            2 -> this.setText("Female")
            else -> this.setText("Other")
        }
    }
}

fun ImageView.setAdv(con: Context, imgUrl: String?) {
    if (imgUrl.isNullOrEmpty()) {
        this.gone()
    } else
        Glide.with(con).load(imgUrl).into(this)
}

fun ImageView.set(imgUrl: String?, tranformation: RoundedCornersTransform) {
    if (imgUrl.isNullOrEmpty())
        Picasso.get().load(R.drawable.placeholder).into(this)
    else
        Picasso.get().load(imgUrl).transform(tranformation).placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder).into(this)
}

fun EditText.onTextChange(view: View) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            view?.visibility = android.view.View.GONE
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun String.changeToLongDate(): Long {
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.parse(this)
        val longDate = date.time
        return longDate
    } catch (e: ParseException) {
        e.printStackTrace()
        return 0
    }
}

fun String.changeTimeFormat(from: String?, to: String?): String? {
    if (TextUtils.isEmpty(this)) return ""
    var date1: Date? = null
    val simpleDateFormat =
        SimpleDateFormat(from, Locale.getDefault())
    val simpleDateFormat1 =
        SimpleDateFormat(to, Locale.getDefault())
    try {
        date1 = simpleDateFormat.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return simpleDateFormat1.format(date1)
}

fun String.isAfter(date: String?, dateFormate: String): Boolean {
    val dfDate = SimpleDateFormat(dateFormate)
    var b = false
    try {
        if (dfDate.parse(this).before(dfDate.parse(date))) {
            b = false // If start date is before end date.
        } else if (dfDate.parse(this).equals(dfDate.parse(date))) {
            b = false // If start date is before end date.
        } else if (dfDate.parse(this).after(dfDate.parse(date))) {
            b = true // If start date is after the end date.
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return b
}

fun String.isAfterOrEqual(date: String?, dateFormate: String): Boolean {
    val dfDate = SimpleDateFormat(dateFormate)
    var b = false
    try {
        if (dfDate.parse(this).before(dfDate.parse(date))) {
            b = false // If start date is before end date.
        } else if (dfDate.parse(this).equals(dfDate.parse(date))) {
            b = true // If start date is before end date.
        } else if (dfDate.parse(this).after(dfDate.parse(date))) {
            b = true // If start date is after the end date.
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return b
}

fun String.isBefore(date: String?, dateFormate: String): Boolean {
    val dfDate = SimpleDateFormat(dateFormate)
    var b = false
    try {
        if (dfDate.parse(this).before(dfDate.parse(date))) {
            b = true // If start date is before end date.
        } else if (dfDate.parse(this).equals(dfDate.parse(date))) {
            b = false // If start date is before end date.
        } else if (dfDate.parse(this).after(dfDate.parse(date))) {
            b = false // If start date is after the end date.
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return b
}

fun View.hideShow(list: List<Any>?, recyclerView: View) {
    if (list != null && list.isNotEmpty()) {
        this.gone()
        recyclerView.visible()
    } else {
        this.visible()
        recyclerView.gone()
    }
}

fun View.shake() {
    YoYo.with(Techniques.Shake).duration(500).playOn(this)
    val v = MainApplication.get().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    // Vibrate for 500 milliseconds
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v.vibrate(
            VibrationEffect.createOneShot(
                200,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        //deprecated in API 26
        v.vibrate(200)
    }
}

fun View.push(): PushDownAnim? {
    return PushDownAnim.setPushDownAnimTo(this)
}

fun View.pushTab(): PushDownAnim? {
    var pushDownAnimTo = PushDownAnim.setPushDownAnimTo(this)
    pushDownAnimTo.setScale(0.90f)
    return pushDownAnimTo
}

fun ImageView.loadImage(con: Context, url: String?) {
    if (url.isEmptyy())
        Glide.with(con).load(R.drawable.placeholder).into(this)
    else
        Glide.with(con).load(url).placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
            .into(this)
}

fun String.toast() {
    MainApplication.getActivityInstance().runOnUiThread {
        val inflater = MainApplication.get()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.layout_toast_custom, null, false)
        val tv = layout.findViewById(R.id.txtvw) as TextView
        tv.text = this
        val toast = Toast(MainApplication.get())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.setGravity(Gravity.CENTER, 0, 100)
        toast.show()
    }
}

fun String.showWarning() {
    MainApplication.getActivityInstance().let {
        Util.showWarningSneaker(it, this)
    }
}

fun String.log() {
    Log.d(MainApplication.getActivityInstance().packageName, this)
}

fun String.showSuccess() {
    MainApplication.getActivityInstance().let {
        Util.showSuccessSneaker(it, this)
    }
}

fun String.showError() {
    MainApplication.getActivityInstance().let {
        Util.showErrorSneaker(it, this)
    }
}

fun String.showWarning(view: View) {
    Util.showWarningSneaker(MainApplication.getActivityInstance(), this, view)
}

fun View.push(clickListener: View.OnClickListener) {
    PushDownAnim.setPushDownAnimTo(this).setOnClickListener { clickListener }
}

fun Context.setDrawable(imgview: AppCompatImageView?, drawable: Int?) {
    imgview.let {
        drawable.let {
            imgview!!.setImageDrawable(this.resources.getDrawable(drawable!!))
        }
    }
}

fun Context.setTvColor(tv: TextView?, color: Int?) {
    tv.let {
        color.let {
            try {
                tv!!.setTextColor(this.resources.getColor(color!!))
            } catch (e: Exception) {
            }
        }
    }

}

fun Context.setTvBGColor(tv: TextView?, color: Int?) {
    tv.let {
        color.let {
            try {
                tv!!.setBackground(this.resources.getDrawable(color!!))
            } catch (e: Exception) {
            }
        }
    }

}

fun Context.setTvBGDrawable(tv: TextView?, color: Int?) {
    tv.let {
        color.let {
            try {
                tv!!.setBackground(this.resources.getDrawable(color!!))
            } catch (e: Exception) {
            }
        }
    }
}

fun View?.visible() {
    this?.visibility = View.VISIBLE
//    YoYo.with(Techniques.SlideInUp).onStart { this?.visibility = View.VISIBLE }.playOn(this)
}

fun View.invisible() {
    this?.visibility = View.INVISIBLE
//    YoYo.with(Techniques.SlideOutDown).onEnd { this?.visibility = View.INVISIBLE }.playOn(this)
}

fun View.gone() {
    this?.visibility = View.GONE
}

fun Context.titleWithLogo(str: Int): Spanny? {
    return Spanny("")
        .append(" ".plus(this.getString(str)), ImageSpan(this, R.drawable.logo_icon))
}

fun Context.highlight(str: String): Spanny? {
    return Spanny("")
        .append(str, UnderlineSpan(), ForegroundColorSpan(Color.RED))
}

fun EditText.enable(bool: Boolean) {
    this.isFocusable = bool
    this.isFocusableInTouchMode = bool
    this.isCursorVisible = bool
}

fun String?.isEmptyy(): Boolean {

    if (this == null)
        return true
    else if (this.trim().isEmpty())
        return true
    else if (this.trim().length > 0 && this.trim().equals("null", true))
        return true

    return false
}

fun ArrayList<String>.toCommaSeparated(): String {
    var builder = StringBuilder("")

    for (str in this)
        if (!str.isEmptyy())
            builder.append("$str,")

    return builder.toString().removeSuffix(",")
}

fun ArrayList<String>.toCommaSeparatedString(): String {
    var builder = StringBuilder("")

    for (str in this)
        if (!str.isEmptyy())
            builder.append("$str, ")

    return builder.toString().removeSuffix(", ")
}

fun String?.nul(): String {

    if (this == null)
        return ""
    else if (this.trim().isEmpty())
        return ""
    else if (this.trim().length > 0 && this.trim().equals("null", true))
        return ""

    return this
}

fun String.isNotEmptyy(): Boolean {

    if (this == null)
        return false
    else if (this.trim().isEmpty())
        return false
    else if (this.trim().length > 0 && this.trim().equals("null", true))
        return false

    return true
}

fun String.openInBrowser() {
    val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(this))
    MainApplication.getActivityInstance().startActivity(intent)
}

fun String.isNumber(): Boolean {
    return try {
        if (!this.isEmptyy())
            this.replace("+", "").replace("-", "").trim().toLong()
        return true
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.hasPlusAndDash(): Boolean {
    if (this.isEmptyy())
        return false
    else return !(!this.startsWith("+") || !this.contains("-"))
}

fun Context.shareApp() {
    val appPackageName = this.packageName
    val sendIntent = Intent()
    sendIntent.setAction(Intent.ACTION_SEND)
    sendIntent.putExtra(
        Intent.EXTRA_TEXT,
        "Check out the App at: https://play.google.com/store/apps/details?id=$appPackageName"
    )
    sendIntent.setType("text/plain")
    this.startActivity(sendIntent)
}

fun View.disable() {
//    alpha = .5f
    isClickable = false
}

fun View.enable() {
//    setAlpha(1f)
    isClickable = true
}

@Suppress("UNCHECKED_CAST")
fun <F : Fragment> AppCompatActivity.getCurrentFragment(fragmentClass: Class<F>): F? {
    if (this.supportFragmentManager.fragments.isEmpty()) return null
    val navHostFragment = this.supportFragmentManager.fragments.first() as? NavHostFragment

    navHostFragment?.childFragmentManager?.fragments?.forEach {
        if (fragmentClass.isAssignableFrom(it.javaClass)) {
            return it as F
        }
    }
    return null
}

fun String.sendToServer() {

    if (Prefs.get().loginData != null) {
        val headers = HashMap<String, String>()

        headers["Content-Type"] = "application/json"
        headers["ApiServiceToken"] = "U3System@2020"
        headers["Offset"] = "1"
        headers["AppVersion"] = "1.0"
        headers["DeviceType"] = "2"

        Prefs.get().loginData?.apply {
            headers["Token"] = "$token"
            headers["UserId"] = "$id"
        }

        AndroidNetworking.post(BASE_URL + UPDATE_TOKEN_TO_SERVER)
            .addHeaders(headers)
            .addJSONObjectBody(JSONObject().put("DeviceToken", this))
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    response.toString().log()
                }

                override fun onError(anError: ANError?) {
                    anError?.toString()?.log()
                }
            })
    }
}

fun View.touch() {
    val downTime: Long = SystemClock.uptimeMillis()
    val eventTime: Long = SystemClock.uptimeMillis() + 100

    val x = this.getX() + this.getWidth()  / 2
    val y = this.getY() + this.getHeight() / 2

    val metaState = 0
    val motionEvent = MotionEvent.obtain(
        downTime,
        eventTime,
        MotionEvent.ACTION_UP,
        x,
        y,
        metaState
    )
    this.dispatchTouchEvent(motionEvent)
}

fun String.sendNotification() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = MainApplication.getActivityInstance().getString(R.string.channel_name)
        val description = MainApplication.getActivityInstance().getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(MainApplication.getActivityInstance().getString(R.string.channel_id), name, importance)
        channel.description = description
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = MainApplication.getActivityInstance().getSystemService(NotificationManager::class.java)
        notificationManager!!.createNotificationChannel(channel)
    }

    val notifyIntent = Intent(MainApplication.getActivityInstance(), HomeActivity::class.java)
    notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    var notifyPendingIntent = PendingIntent.getActivity(MainApplication.getActivityInstance(), 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)


    val mBuilder = NotificationCompat.Builder(MainApplication.getActivityInstance(), MainApplication.getActivityInstance().getString(R.string.channel_id))
        .setSmallIcon(R.drawable.logo_icon)
        .setContentTitle(this)
        .setContentText(this)
        .setContentIntent(notifyPendingIntent)
        .setAutoCancel(true)
        //.setLargeIcon(bmp)
        .setColor(ContextCompat.getColor(MainApplication.getActivityInstance(), R.color.colorPrimary))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()


    /* to remove notification after click*/
    mBuilder.flags = mBuilder.flags or Notification.FLAG_AUTO_CANCEL

    val notificationManager = NotificationManagerCompat.from(MainApplication.getActivityInstance())
    notificationManager.notify(System.currentTimeMillis().toInt(), mBuilder)

}

fun vibrate(millis: Long) {
    val v =
        MainApplication.getActivityInstance().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    // Vibrate for 500 milliseconds
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v.vibrate(
            VibrationEffect.createOneShot(
                millis,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        //deprecated in API 26
        v.vibrate(millis)
    }
}
