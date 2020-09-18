package com.three.u.base

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phelat.navigationresult.BundleFragment
import com.thekhaeng.pushdownanim.PushDownAnim
import com.three.u.R
import com.three.u.model.response.AdvModel
import com.three.u.model.response.MasterResponse
import com.three.u.util.Prefs
import com.three.u.util.Util
import com.three.u.networking.Api
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


open class BaseFragment : BundleFragment() {

    internal var duration = 2000
    var commonCallbacks: CommonCallbacks? = null
    fun getViewModelProvider(): MyViewModelProvider =
        MyViewModelProvider(commonCallbacks as AsyncViewController)

    fun showSuccessOrErrorAndGoBack(it:MasterResponse<Any>){
        if (it != null && it.responseCode == 200)
            goBack(it.successMsg)
        else
            showErrorBar(it.failureMsg)
    }

    fun showSuccessOrErrorAndGoBackk(it:MasterResponse<Boolean>){
        if (it != null && it.responseCode == 200)
            goBack(it.successMsg)
        else
            showErrorBar(it.failureMsg)
    }



    fun showSuccessOrErrorandGoBack(it:MasterResponse<*>?){
        if (it != null && it.responseCode == 200)
            goBack(it.successMsg)
        else
            showErrorBar(it?.failureMsg?: getString(R.string.something_went_wrong))
    }

    fun showSuccessOrError(it:MasterResponse<*>?){
        if (it != null && it.responseCode == 200)
            showSuccessBar(it.successMsg.nul())
        else
            showErrorBar(it?.failureMsg.nul())
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
                val date: Date = calendar.getTime()
                dob = formatForServer.format(date)
                val currentDate: String = SimpleDateFormat(format).format(Date())

                val isTodayOrAfterToday: Boolean = Util.isTodayOrAfterToday(currentDate, dob, format)
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
            val mills: Long = date!!.getTime()
            datePickerDialog.getDatePicker().minDate = mills
        }
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -18)
        datePickerDialog.getDatePicker().setMinDate(getCurrentDate())
        datePickerDialog.show()
        return dateTimeLiveData
    }

    open fun getCurrentDate(): Long {
        return Calendar.getInstance().getTimeInMillis()
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
            Log.e("Error",e.message+"")
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
            Log.e("Error",e.message+"")
        }

    }

    fun <T> nullCheck(str: T?): T? {
        return str ?: "" as T
    }

    open fun showImageDialog(url:String) {
        val dialog = context?.let { Dialog(it, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen) }
        dialog?.show()
        dialog?.setCancelable(true)
        dialog?.setContentView(R.layout.dialog_image)

        var img: ImageView? = dialog?.findViewById(R.id.img)
        context?.let { img?.set(it,url) }

        val crossView: ImageView? = dialog?.findViewById(R.id.cross)
        crossView?.push()?.setOnClickListener { dialog.dismiss() }

    }

    fun getAdvList(type:Int) : ArrayList<AdvModel?>?{
        var advData = Prefs.get().ADV_DATA
        var arrayList : List<AdvModel?>? = null

        if(!advData.isEmptyy()){
            val advData = Prefs.get().ADV_DATA
            if(!advData.isEmptyy()){
                val list = Gson().fromJson<List<AdvModel?>?>(advData, object : TypeToken<List<AdvModel?>?>() {}.getType())
                arrayList = list?.filter { it?.advertisementType == type }
            }
        }
        return arrayList as ArrayList<AdvModel?>?
    }

    fun onAdClick(url:String) {
        if(url.isEmptyy())
            return

        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        activity?.startActivityForResult(openURL,987)
    }

    fun showAdv(type:Int, imageView:ImageView, sectionId:Int){

        var adv : AdvModel? = null

        getAdvList(type)?.let {
            if(it !=null && it.size>0 && it.isNotEmpty())
                context?.let { con ->
                    adv = it.random()
                    imageView.setAdv(con,adv?.advertisementImage)
                    imageView.push()?.setOnClickListener {
                        adv?.advertisementURL?.let { adUrl -> onAdClick(adUrl) }
                    }
                }
            else imageView.gone()

        }?:run{
            imageView.gone()
        }

        adv?.advertisementId?.let { callAdvCountApi(it, sectionId) }
    }

    fun callAdvCountApi(advId : Int, advSectionId : Int){

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

        AndroidNetworking.post(Api.BASE_URL+"PromotionAdvertisement/UpdateAdvertisementAccess")
            .addHeaders(headers)
            .addJSONObjectBody(JSONObject().put("AdvertismentId",advId).put("AdvertisementSectionId",advSectionId))
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    response.toString().log()
                }

                override fun onError(anError: ANError?) {
                    anError?.toString()?.log()
                }
            })
    }

}