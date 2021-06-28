package com.architecture.networking;


import com.google.gson.JsonObject;
import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiInterface {

    @POST
    Call<ResponseBody> postApi(@HeaderMap Map<String, String> headers, @Url String url, @Body JsonObject request);

    @GET
    Call<ResponseBody> getApi(@HeaderMap Map<String, String> headers, @Url String url);

    /*@Multipart
    @POST
    Call<ResponseBody> postMultipart(
            @HeaderMap Map<String, String> headers,
            @Url String url,
            @Part("JsonModel") RequestBody dataMap,
            @Part MultipartBody.Part file
    );*/

    @POST
    Call<ResponseBody> postMultipart(
            @HeaderMap Map<String, String> headers,
            @Url String url,
            @Body RequestBody body
    );

}
