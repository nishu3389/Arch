package com.architecture.base

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.phelat.navigationresult.BundleFragment
import com.thekhaeng.pushdownanim.PushDownAnim
import com.architecture.R
import com.architecture.model.response.MasterResponse
import com.architecture.util.Util
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


open class BaseFragment : BundleFragment() {

    internal var duration = 2000
    var commonCallbacks: CommonCallbacks? = null
    fun getViewModelProvider(): MyViewModelProvider =
        MyViewModelProvider(commonCallbacks as AsyncViewController)

    fun showSuccessOrErrorAndGoBack(it: MasterResponse<Any>){
        if (it != null && it.responseCode == 200)
            goBack(it.message)
        else
            showErrorBar(it.message)
    }

    fun showSuccessOrErrorAndGoBackkk(it: MasterResponse<*>){
        if (it != null && it.responseCode == 200)
            goBack(it.message)
        else
            showErrorBar(it.message)
    }

    fun showSuccessOrErrorAndGoBackk(it: MasterResponse<Boolean>){
        if (it != null && it.responseCode == 200)
            goBack(it.message)
        else
            showErrorBar(it.message)
    }



    fun showSuccessOrErrorandGoBack(it: MasterResponse<*>?){
        if (it != null && it.responseCode == 200)
            goBack(it.message)
        else
            showErrorBar(it?.message ?: getString(R.string.something_went_wrong))
    }

    fun showSuccessOrError(it: MasterResponse<*>?){
        if (it != null && it.responseCode == 200)
            showSuccessBar(it.message.nul())
        else
            showErrorBar(it?.message.nul())
    }

    fun toast(str: String) {
        str.let {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show()
        }
    }

    fun push(view: View, clickListener: View.OnClickListener) {
        PushDownAnim.setPushDownAnimTo(view).setOnClickListener { clickListener }
    }

    override fun onAttach(context: Context) {
        commonCallbacks = context as CommonCallbacks
        super.onAttach(context)
    }

    override fun onDetach() {
        commonCallbacks = null
        super.onDetach()
    }

    fun showProgress() {
        commonCallbacks?.showProgressDialog()
    }

    fun hideProgress() {
        commonCallbacks?.hideProgressDialog()
    }

    fun showAlertDialog(msg: String, btnListener: DialogInterface.OnClickListener?) {
        commonCallbacks?.showAlertDialog(msg, btnListener)
    }

    fun hideAlertDialog() {
        commonCallbacks?.hideAlertDialog()
    }

    open fun handlingBackPress(): Boolean {
        return findNavController().navigateUp()
    }

    open fun onFilePicked(pickedFileUri: String) {}

    open fun onApiRequestFailed(apiUrl: String, errCode: Int, errorMessage: String): Boolean {
        return false
    }

    open fun onSubscriptionExpired() {

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun navigate(fragmentId: Int, vararg data: Pair<String, Any?>) {
        val b = bundleOf(*data)
        findNavController().navigate(fragmentId, b)
    }

    fun navigate(fragmentId: Int) {
        findNavController().navigate(fragmentId)
    }

    fun getParcelOrNull(fragmentId: Int): Bundle? {
        return commonCallbacks!!.getSharedModel().parcels[fragmentId]
    }

    fun getParcelAndConsume(fragmentId: Int, consumer: (b: Bundle) -> Unit) {
        val parcelMap = commonCallbacks!!.getSharedModel().parcels
        parcelMap[fragmentId]?.apply {
            consumer(this)
            parcelMap.remove(fragmentId)
        }
    }

    open fun putParcel(parcel: Bundle, fragmentId: Int) {
        commonCallbacks!!.getSharedModel().parcels[fragmentId] = parcel
    }

    open fun onReceiveLocation(newLocation: Location?): Boolean {
        return false
    }

    var dateTimeLiveData: MutableLiveData<String>? = null
    var dob: String = ""

    /**
     * Pick dob.
     */
    open fun pickDate(format: String?): MutableLiveData<String>? {
        dateTimeLiveData = MutableLiveData()
        (activity as BaseActivity).hideKeyboard()
        val mcurrentTime: Calendar = Calendar.getInstance()
        val year: Int = mcurrentTime.get(Calendar.YEAR)
        val month: Int = mcurrentTime.get(Calendar.MONTH)
        val dayOfMonth: Int = mcurrentTime.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, year1, month1, dayOfMonth1 ->
                val formatForServer = SimpleDateFormat(format)
                val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
                calendar.set(Calendar.YEAR, year1)
                calendar.set(Calendar.MONTH, month1)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth1)
                val date: Date = calendar.time
                dob = formatForServer.format(date)
                val currentDate: String = SimpleDateFormat(format).format(Date())

                val isTodayOrAfterToday: Boolean = Util.isTodayOrAfterToday(
                    currentDate,
                    dob,
                    format
                )
//                if (!isTodayOrAfterToday) {
//                    showWarning(context, "Availability date cannot before present date")
//                    toast("Availability date cannot before present date")
//                    dob = ""
//                } else

                dateTimeLiveData!!.setValue(dob)
            }, year, month, dayOfMonth)
        datePickerDialog.setOnCancelListener { dialog ->
            dob = ""
            dateTimeLiveData!!.setValue("")
        }
        val currentDate: String = SimpleDateFormat(format).format(Date())
        val formatter = SimpleDateFormat(format)
        var date: Date? = null
        try {
            date = formatter.parse(currentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        date.let {
            val mills: Long = date!!.time
            datePickerDialog.datePicker.minDate = mills
        }
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -18)
        datePickerDialog.datePicker.minDate = getCurrentDate()
        datePickerDialog.show()
        return dateTimeLiveData
    }

    open fun getCurrentDate(): Long {
        return Calendar.getInstance().timeInMillis
    }

    fun showSuccessBar(msg: String) {
        context.let {
            Util.showSuccessSneaker(requireContext(), msg)
        }
    }

    fun showErrorBar(msg: String) {
        context.let {
            Util.showErrorSneaker(requireContext(), msg)
        }
    }

    fun showErrorBar(msg: String, view: View) {
        context.let {
            Util.showErrorSneaker(requireContext(), msg, view)
        }
    }

    fun showWarning(msg: String) {
        context.let {
            Util.showWarningSneaker(requireContext(), msg)
        }
    }

    fun showWarning(msg: String, view: View) {
        context.let {
            Util.showWarningSneaker(requireContext(), msg, view)
        }
    }

    fun goBack(message: String?) {
        try {
            message?.let {
                activity.let {
                    showSuccessBar(message)
                    if(activity!=null) {
                        if ((activity as? BaseActivity) != null) {
                            Handler().postDelayed(
                                { (activity as? BaseActivity)?.forceBack() },
                                duration.toLong()
                            )
                        }
                    }

                }

            }
        } catch (e: Exception) {
            Log.e("Error", e.message + "")
        }

    }

    fun goBack() {
        try {
            if(activity!=null) {
                if ((activity as? BaseActivity) != null) {
                    Handler().postDelayed(
                        { (activity as? BaseActivity)?.forceBack() },
                        duration.toLong()
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.message + "")
        }

    }

    fun goBack(duration: Long) {
        try {
            if(activity!=null) {
                if ((activity as? BaseActivity) != null) {
                    Handler().postDelayed(
                        { (activity as? BaseActivity)?.forceBack() },
                        duration
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.message + "")
        }

    }

    fun <T> nullCheck(str: T?): T? {
        return str ?: "" as T
    }

    public open fun showImageDialog(url: String) {
        val dialog = context?.let { Dialog(
            it,
            android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen
        ) }
        dialog?.show()
        dialog?.setCancelable(true)
        dialog?.setContentView(R.layout.dialog_image)

        var img: ImageView? = dialog?.findViewById(R.id.img)
        context?.let { img?.set(it, url) }

        val crossView: ImageView? = dialog?.findViewById(R.id.cross)
        crossView?.push()?.setOnClickListener { dialog.dismiss() }

    }

    public open fun showVideoDialog(url: String) {
        val dialog = context?.let { Dialog(
            it,
            android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen
        ) }
        dialog?.show()
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_video)

//        var videoView: ExoVideoView? = dialog?.findViewById(R.id.videoView)
//        val mediaSource = SimpleMediaSource(url) //uri also supported
//        videoView?.play(mediaSource)
//
//        videoView?.changeWidgetVisibility(R.id.exo_player_controller_back, View.INVISIBLE);
//
//        videoView?.setOrientationListener { orientation: Int ->
//            if (orientation == SENSOR_PORTRAIT) {
//                //do something
//                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
//            } else if (orientation == SENSOR_LANDSCAPE) {
//                //do something
//                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
//            }
//        }

        val crossView: ImageView? = dialog?.findViewById(R.id.cross)
        crossView?.push()?.setOnClickListener {
//            videoView?.releasePlayer()
            dialog.dismiss()
        }

    }



    fun setClickable(
        textView: TextView,
        subString: String,
        handler: () -> Unit,
        drawUnderline: Boolean = false
    ) {
        val text = textView.text
        val start = text.indexOf(subString)
        val end = start + subString.length

        val span = SpannableString(text)
        span.setSpan(
            ClickHandler(handler, drawUnderline),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.linksClickable = true
        textView.isClickable = true
        textView.movementMethod = LinkMovementMethod.getInstance()

        textView.text = span
    }

    class ClickHandler(
        private val handler: () -> Unit,
        private val drawUnderline: Boolean
    ) : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            if (drawUnderline) {
                super.updateDrawState(ds)
            } else {
                ds.isUnderlineText = false
            }
        }

        override fun onClick(p0: View) {
            handler()
        }

    }

}