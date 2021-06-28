package com.architecture.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.architecture.R
import com.architecture.base.MainApplication
import com.architecture.base.get
import java.io.ByteArrayOutputStream
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Util {

    companion object {

        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS

        fun convertUtcToLocal(ourDate: String?, inputFormat: String? = Constant.API_DATE_FORMAT, outputFormat: String? = Constant.API_DATE_FORMAT): String? {
            var ourDate = ourDate
            try {
                val formatter = SimpleDateFormat(inputFormat)
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                val value = formatter.parse(ourDate)
                val dateFormatter = SimpleDateFormat(outputFormat) //this format changeable
                dateFormatter.timeZone = TimeZone.getDefault()
                ourDate = dateFormatter.format(value)

                //Log.d("ourDate", ourDate);
            } catch (e: java.lang.Exception) {
                ourDate = "00-00-0000 00:00"
            }
            return ourDate
        }

        private fun currentDate(): Date {
            val calendar = Calendar.getInstance()
            return calendar.time
        }

        fun getTimeAgo(strDate: String, format: String?): String {

            val format = SimpleDateFormat(format)
            try {
                val date = format.parse(strDate)

                var time = date.time
                if (time < 1000000000000L) {
                    time *= 1000
                }

                val now = currentDate().time
                if (time > now || time <= 0) {
                    return "in the future"
                }

                val diff = now - time
                return when {
                    diff < MINUTE_MILLIS -> "moments ago"
                    diff < 2 * MINUTE_MILLIS -> "a minute ago"
                    diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
                    diff < 2 * HOUR_MILLIS -> "an hour ago"
                    diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
                    diff < 48 * HOUR_MILLIS -> "yesterday"
                    else -> "${diff / DAY_MILLIS} days ago"
                }
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }


        }


        private fun getNextDate(date: String?) : String {
            val dateToIncr = date
            var nextDate = ""
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val c = Calendar.getInstance()
            try {
                c.time = sdf.parse(dateToIncr)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            c.add(Calendar.DAY_OF_MONTH, 1) // number of days to add

            nextDate = SimpleDateFormat("dd MMM").format(c.time)
            return nextDate
        }

        private fun getPreviousDate(date: String?) : String {
            val dateToIncr = date
            var nextDate = ""
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val c = Calendar.getInstance()
            try {
                c.time = sdf.parse(dateToIncr)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            c.add(Calendar.DAY_OF_MONTH, -1) // number of days to add

            nextDate = SimpleDateFormat("dd MMM").format(c.time)
            return nextDate
        }



        fun changeTimeFormat(d: String?, from: String?, to: String?): String? {
            if (TextUtils.isEmpty(d)) return ""
            var date1: Date? = null
            val simpleDateFormat =
                SimpleDateFormat(from, Locale.getDefault())
            val simpleDateFormat1 =
                SimpleDateFormat(to, Locale.getDefault())
            try {
                date1 = simpleDateFormat.parse(d)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return simpleDateFormat1.format(date1)
        }

        /**
         * Is today or after today boolean.
         *
         * @param currentDate the current date
         * @param myDate      the my date
         * @return the boolean
         */
        fun isTodayOrAfterToday(currentDate: String?, myDate: String?): Boolean {
            val dfDate = SimpleDateFormat("dd-MM-yyyy")
            var isTodayOrAfterToday = false
            try {
                if (dfDate.parse(currentDate).before(dfDate.parse(myDate))) {
                    isTodayOrAfterToday = true // If currentDate is before myDate.
                }
                if (dfDate.parse(currentDate) == dfDate.parse(myDate)) {
                    isTodayOrAfterToday = true // If two dates are equal.
                }
                if (dfDate.parse(currentDate).after(dfDate.parse(myDate))) {
                    isTodayOrAfterToday = false // If currentDate is after the myDate.
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return isTodayOrAfterToday
        }

        fun isTodayOrAfterToday(currentDate: String?, myDate: String?, formate: String?): Boolean {
            val dfDate = SimpleDateFormat(formate)
            var isTodayOrAfterToday = false
            try {
                if (dfDate.parse(currentDate).before(dfDate.parse(myDate))) {
                    isTodayOrAfterToday = true // If currentDate is before myDate.
                }
                if (dfDate.parse(currentDate) == dfDate.parse(myDate)) {
                    isTodayOrAfterToday = true // If two dates are equal.
                }
                if (dfDate.parse(currentDate).after(dfDate.parse(myDate))) {
                    isTodayOrAfterToday = false // If currentDate is after the myDate.
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return isTodayOrAfterToday
        }

        fun log(msg: String) {
            Log.d("APP_LOGS", msg)
        }

        fun toast(s: String) {
            Toast.makeText(MainApplication.get().getContext(), s, Toast.LENGTH_SHORT).show()
        }

        fun getUriFromFilePath(path: String): Uri {
            return Uri.fromFile(File(path))
        }

        fun areAllPermissionsAccepted(grantResults: IntArray): Boolean {
            grantResults.forEach {
                if (it != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }

        fun getUriStringFromPath(path: String): String {
            return "file://$path"
        }

        fun isAPdf(path: String): Boolean {
            val extension = path.substring(path.lastIndexOf("."))
            return extension.equals(".pdf", ignoreCase = true)
        }

        fun contextFrom(v: View): Context {
            return v.context
        }

        fun getDrawable(id: Int): Drawable {
            return ContextCompat.getDrawable(MainApplication.get(), id)!!
        }

        fun getColor(id: Int): Int {
            return ContextCompat.getColor(MainApplication.get(), id)
        }

        fun getUrlListFromString(str: String): List<String> {
            var data: String = str
            if (str[str.lastIndex] == ',') {
                data = str.substring(0, str.lastIndex)
            }
            return data.split(",").toList()
        }


        var serverdateFormat = "yyyy-M-dd HH:mm:ss"

        fun convertServerDateToUserTimeZone(serverDate: String): String {
            var ourdate: String
            try {
                val formatter = SimpleDateFormat(serverdateFormat, Locale.UK)
                formatter.timeZone = TimeZone.getTimeZone("UTC")
                val value = formatter.parse(serverDate)
                //val timeZone = TimeZone.getTimeZone("Asia/Kolkata")
                val timeZone = TimeZone.getDefault()
                val dateFormatter =
                    SimpleDateFormat(serverdateFormat, Locale.UK) //this format changeable
                dateFormatter.timeZone = timeZone
                ourdate = dateFormatter.format(value)

                //Log.d("OurDate", OurDate);
            } catch (e: Exception) {
                ourdate = "0000-00-00 00:00:00"
            }



            return ourdate
        }

        fun updateStatusBarColor(color: String, acti: FragmentActivity) {
            // Color must be in hexadecimal fromat
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = acti?.window
                window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window?.setStatusBarColor(Color.parseColor(color))

            }
        }

        fun updateStatusBarColor(color: Int, acti: FragmentActivity) {
            // Color must be in hexadecimal fromat
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = acti?.window
                window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window?.setStatusBarColor(ContextCompat.getColor(MainApplication.get(),color))

            }
        }

        fun convertDateOnly(time: String): String? {
            val inputPattern = "yyyy-M-dd HH:mm:ss"
            val outputPattern = "dd MMM yyyy"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)

            var date: Date? = null
            var str: String? = null

            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return str
        }

        fun isAfter(startDate: String?, endDate: String?, dateFormate: String): Boolean {
            val dfDate = SimpleDateFormat(dateFormate)
            var b = false
            try {
                if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                    b = false // If start date is before end date.
                } else if (dfDate.parse(startDate) == dfDate.parse(endDate)) {
                    b = false // If two dates are equal.
                } else if (dfDate.parse(startDate).after(dfDate.parse(endDate))) {
                    b = true // If start date is after the end date.
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return b
        }

        fun covertTimeToText(data: String): String? {

            var dataDate: String = convertServerDateToUserTimeZone(data)

            var convTime: String? = null

            val prefix = ""
            val suffix = " ago"

            try {
                val dateFormat = SimpleDateFormat("yyyy-M-dd HH:mm:ss")
                val pasTime: Date = dateFormat.parse(dataDate)

                val nowTime = Date()

                val dateDiff: Long = nowTime.time - pasTime.time

                val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
                val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
                val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
                val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)

                if (second < 60) {
                    convTime = second.toString() + "s " + suffix
                } else if (minute < 60) {
                    //Minutes
                    convTime = minute.toString() + "m " + suffix
                } else if (hour < 24) {
                    convTime = hour.toString() + "h " + suffix
                } else if (day >= 7) {
                    if (day > 360) {
                        //Years
                        convTime = (day / 30).toString() + "y " + suffix
                    } /*else if (day > 30) {
                    //Months
                    convTime = (day / 360).toString() + " Months " + suffix
                } */ else {
                        //Week
                        convTime = (day / 7).toString() + "w" + suffix
                    }
                } else if (day < 7) {
                    //Days
                    convTime = day.toString() + "d " + suffix
                }

            } catch (e: ParseException) {
                e.printStackTrace()
                e.message?.let { Log.e("ConvTimeE", it) }
            }

            return convTime
        }

        fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
            val output =
                Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)

            val color = -0xbdbdbe
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())
            val rectF = RectF(rect)
            val roundPx = pixels.toFloat()

            paint.setAntiAlias(true)
            canvas.drawARGB(0, 0, 0, 0)
            paint.setColor(color)
            canvas.drawRoundRect(rectF, 100F, 100F, paint)

            paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
            canvas.drawBitmap(bitmap, rect, rect, paint)

            return output
        }
        fun getFileToByte(filePath: String): String? {
            var bmp: Bitmap? = null
            var bos: ByteArrayOutputStream? = null
            var bt: ByteArray? = null
            var encodeString: String? = null
            try {
                bmp = BitmapFactory.decodeFile(filePath)
                bos = ByteArrayOutputStream()
                bmp!!.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                bt = bos!!.toByteArray()
                encodeString = Base64.encodeToString(bt, Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return encodeString
        }
        fun getKeyHash(context: Activity): String? {
            var key: String? = null
            try {
                val packageName = context.applicationContext.packageName
                val info = context.packageManager.getPackageInfo(packageName,
                                                                 PackageManager.GET_SIGNATURES)
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    key = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                    Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                }
            } catch (e: PackageManager.NameNotFoundException) {

            } catch (e: NoSuchAlgorithmException) {

            }
            return key
        }
        public fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val theta = lon1 - lon2
            var dist = (Math.sin(deg2rad(lat1))
                    * Math.sin(deg2rad(lat2))
                    + (Math.cos(deg2rad(lat1))
                    * Math.cos(deg2rad(lat2))
                    * Math.cos(deg2rad(theta))))
            dist = Math.acos(dist)
            dist = rad2deg(dist)
            dist = dist * 60 * 1.1515
            return dist
        }

        private fun deg2rad(deg: Double): Double {
            return deg * Math.PI / 180.0
        }

        private fun rad2deg(rad: Double): Double {
            return rad * 180.0 / Math.PI
        }



        fun showErrorSneaker(context: Context, msg: String?) {
            Sneaker.with(context as Activity)
                .setTitle("Error!")
                .setMessage(msg ?: "Something went wrong, please try again later")
                .setTypeface(Typeface.createFromAsset(context.getAssets(),
                                                      "fonts/poppins_semibold.ttf"))
                .sneakError()
            //        speak(msg);
            vibrate(context, 200)
        }

        fun showErrorSneaker(context: Context, msg: String, view: View?) {
            Sneaker.with(context as Activity)
                .setTitle("Error!")
                .setMessage("$msg")
                .setTypeface(Typeface.createFromAsset(context.getAssets(),
                                                      "fonts/poppins_semibold.ttf"))
                .sneakError()
            vibrate(context, 200)
            //        speak(msg);
            YoYo.with(Techniques.Shake).duration(500).playOn(view)
        }

        fun showSuccessSneaker(context: Context, msg: String?) {
            try {
                Sneaker.with(context as Activity)
                    .setTitle("Success!")
                    .setMessage(msg)
                    .setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/poppins_semibold.ttf"))
                    .sneakSuccess()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        fun showWarningSneaker(context: Context, msg: String?) {
            Sneaker.with(context as Activity)
                .setTitle("Alert")
                .setMessage(msg)
                .setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/poppins_semibold.ttf"))
                .sneakWarning()
            vibrate(context, 200)
        }


        fun showWarningSneaker(context: Context, msg: String?, view: View?) {
            Sneaker.with(context as Activity)
                .setTitle("Alert")
                .setMessage(msg, R.color.white)
                .setIcon(R.drawable.ic_warning)
                .setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/poppins_semibold.ttf"))
                .sneakError()
            YoYo.with(Techniques.Shake).duration(500).playOn(view)
            vibrate(context, 200)
        }


        fun vibrate(context: Context, millis: Long) {
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                //deprecated in API 26
                v.vibrate(millis)
            }
        }


    }

}
