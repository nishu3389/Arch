package com.three.u.model.response

import com.google.gson.annotations.SerializedName
import com.three.u.model.request.ListOfFile
import java.util.*

class ResponseBrowseListItem
{

    @SerializedName("sellItemId")
    var sellItemId: Int? = null

    @SerializedName("userId")
    var userId: Int? = null

    @SerializedName("itemName")
    var itemName:String?=""


    @SerializedName("itemCategoryName")
    var itemCategoryName: String? = ""


    @SerializedName("price")
    var price: String? = ""

    @SerializedName("isForSell")
    var isForSell: Int? = null

    @SerializedName("listOfFiles")
    var listOfFiles: ArrayList<ListOfFile>? = ArrayList<ListOfFile>()



}