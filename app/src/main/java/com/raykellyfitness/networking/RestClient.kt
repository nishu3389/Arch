package com.raykellyfitness.networking

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.raykellyfitness.R
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.MainApplication
import com.raykellyfitness.base.SUBSCRIPTION_EXPIRED
import com.raykellyfitness.base.isEmptyy
import com.raykellyfitness.model.request.WrapperMultiPartRequest
import com.raykellyfitness.model.response.MasterResponse
import com.raykellyfitness.util.Prefs
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class RestClient() {

    val CONNECTION_TIMEOUT = 30
    val RESULT_FAILED = 7
    val RESULT_NO_INTERNET = 8
    val RESULT_UNKNOWN = 9

    var asyncViewController: AsyncViewController? = null
    var apiResponseListener: ApiResponseListener? = null
    private var apiInterface: ApiInterface?
    private val activeApiCalls = ArrayList<Call<*>>()
    private val headers = HashMap<String, String>()
    var gson: Gson

    init {
        apiInterface = getApiInterface()
        headers["Content-Type"] = "application/json"
        headers["ApiServiceToken"] = "U3System@2020"
        headers["Offset"] = "1"
        headers["AppVersion"] = "1.0"
        headers["Devicetype"] = "android"

        gson = GsonBuilder().setLenient().create()

        Prefs.get().loginData?.apply {
            headers["Devicetoken"] = Prefs.get().deviceToken
            headers["Token"] = "$token"
            headers["UserId"] = "$id"
        }

    }


    /**
     * provides retrofit client with proxy implemented api interface
     *
     * @return
     */
    private fun getApiInterface(): ApiInterface? {

        if (apiInterface == null) {
            val client = getOkHttpClient() ?: return null
            val gson = GsonBuilder().setLenient().create()
            val builder = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
            builder.baseUrl(Api.BASE_URL)
            return builder.build().create(ApiInterface::class.java)
        } else {
            return apiInterface
        }
    }

    /**
     * get OkHttpClient
     *
     * @return OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient? {
        try {
            val okClientBuilder = OkHttpClient.Builder()

            okClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            okClientBuilder.readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            okClientBuilder.writeTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)

//            if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okClientBuilder.addInterceptor(httpLoggingInterceptor)
//            okClientBuilder.addInterceptor(ChuckerInterceptor(MainApplication.get().getContext()))
//            }
            return okClientBuilder.build()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * format error string here
     *
     * @param rawError
     * @return
     */
    private fun getError(rawError: String?): String {
        if (rawError == null) {
            return "Error Occurred"
        }
        val formulatedError = MainApplication.get().getContext()
            .getString(R.string.bad_response)
        return if (rawError.contains("JsonReader.setLenient")) {
            formulatedError
        } else if (rawError.contains("Unable to resolve host")) {
            "Couldn't connect to server"
        } else
            rawError
    }

    /**
     * checks network connectivity
     *
     * @return
     */
    private fun isConnectedToNetwork(): Boolean {
        val cm =
            MainApplication.get().getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null
    }

    /**
     * showProgressDialog while api call is active
     */
    private fun showProgressDialog() {
        asyncViewController?.showProgressDialog()
    }

    /**
     * hide progress after api calling
     */
    private fun hideProgressDialog() {
        asyncViewController?.hideProgressDialog()
    }


    /**
     * common checks before any api calling
     *
     * @param String
     * @return
     */
    private fun passChecks(): Boolean {
        if (!isConnectedToNetwork()) {
            asyncViewController?.onNoInternet()
            return false
        }

        return apiInterface != null
    }

    fun getListener(): ApiResponseListener? {
        return apiResponseListener
    }

    fun callApi(String: String, requestPojo: Any?, dataCarrier: MutableLiveData<*>) {
        callApi(String, requestPojo, dataCarrier, true)
    }

    fun callApi(
        code: String,
        requestPojo: Any?,
        dataCarrier: MutableLiveData<*>?,
        showProgressDialog: Boolean
    ) {

        if (!passChecks()) {
            return
        }

        val apiRequestType = Api.getApiRequestType(code)

        // headers["action"] = apiRequestType.action

        var jsonRequest: JsonObject? = null

        if (apiRequestType.requestType !== RequestType.MULTIPART) {
            if (requestPojo != null) {
                val requestStr = gson.toJson(requestPojo)
                jsonRequest = JsonParser().parse(requestStr).asJsonObject
            } else {
                jsonRequest = JsonObject()
            }
        }

        var call: Call<ResponseBody>? = null

        if (apiRequestType.requestType === RequestType.POST) {
            if (apiRequestType.url.contains("\$param")) {
                apiRequestType.url.replace(
                    "\$param",
                    jsonRequest!!.get("_param").asString
                )

            }
            call = getApiInterface()!!.postApi(headers, apiRequestType.url, jsonRequest)

        } else if (apiRequestType.requestType === RequestType.MULTIPART) {

            val requestData = requestPojo as WrapperMultiPartRequest
            val multipartSpecificHeader = HashMap(headers)
            multipartSpecificHeader.remove("Content-Type")
            call = getApiInterface()!!.postMultipart(
                multipartSpecificHeader,
                apiRequestType.url,
                requestData.getMultipartBody()
            )

        } else if (apiRequestType.requestType === RequestType.GET) {

            if (apiRequestType.url.contains("\$param")) {
                apiRequestType.url.replace("\$param", jsonRequest!!.get("_param").asString)
            }
            call = getApiInterface()!!.getApi(headers, apiRequestType.url)

        }

        if(!code.equals(Api.SAVE_PAYMENT))
        jsonRequest?.addProperty("method", code)

        if (showProgressDialog) {
            showProgressDialog()
        }

        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                activeApiCalls.remove(call)

                val responseBody = response.body()
                if (responseBody != null) {
                    val responseString: String
                    try {
                        responseString = responseBody.string()

                        if (responseString != null && responseString.length>1 && responseString.startsWith("{") && responseString.endsWith("}")) {
                            val master: MasterResponse<*> = gson.fromJson(responseString, apiRequestType.responseType)
//                            master.responseCode = SUBSCRIPTION_EXPIRED
                            if (master.responseCode == 200) {
                                dataCarrier?.value = master
                            }else {
                                val wrapperApiError = WrapperApiError(code, master.responseCode, master.message, master.validationErrors)
                                dispatchError(wrapperApiError)
                            }
                        }else
                            showError(response, code)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                } else {

                    showError(response, code)
                }
                hideProgressDialog()

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                activeApiCalls.remove(call)
                val err = getError(t.message)
                val wrapperApiError = WrapperApiError(
                    code,
                    RESULT_FAILED,
                    err
                )
                dispatchError(wrapperApiError)
                hideProgressDialog()
            }
        })
    }

    private fun showError(response: Response<ResponseBody>, code: String) {
        var wrapperApiError: WrapperApiError? = null
        val errBody = response.errorBody()
        if (errBody != null) {
            try {
                val errBodyStr = errBody.string()
                val errInPojo: MasterResponse<*> = gson.fromJson(errBodyStr, MasterResponse::class.java)
                wrapperApiError = WrapperApiError(
                    code,
                    errInPojo.responseCode,
                    errInPojo.message,
                    errInPojo.validationErrors
                )

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                hideProgressDialog()
                wrapperApiError = WrapperApiError(
                    code,
                    RESULT_UNKNOWN,
                    MainApplication.get().getString(R.string.something_went_wrong)
                )
            }
        }else{
            hideProgressDialog()
            wrapperApiError = WrapperApiError(
                code,
                RESULT_UNKNOWN,
                MainApplication.get().getString(R.string.something_went_wrong)
            )
        }
        dispatchError(wrapperApiError!!)
    }

    private fun dispatchError(apiErr: WrapperApiError) {

        var result = apiErr.failureMsg

        if (apiErr.failureMsg.equals("validation error", true) || apiErr.failureMsg.isEmptyy()) {
            result += ":\n"
            apiErr.validationErrors?.forEach {
                result += "\n\n $it"
                result = result.replace(":", "").trim()
            }
        }

        getListener()?.onApiCallFailed(apiErr.apiName, apiErr.responseCode, result) ?: asyncViewController?.onApiCallFailed(apiErr.apiName, apiErr.responseCode, result)
    }

}
