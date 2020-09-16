package com.three.u.model.request

import com.google.gson.annotations.SerializedName
import java.util.*

class RequestUpdateSellItem {

    @SerializedName("SellItemId")
    var sellItemId: Int? = null

    @SerializedName("Description")
    var description: String? = ""
    @SerializedName("SellOrGiveAway")
    var SellOrGiveAway: Int? = null
    var ItemFor: Int? = null
    @SerializedName("ItemCategory")
    var itemCategory: Int? = null
    @SerializedName("ItemName")
    var itemName: String? = ""
    @SerializedName("ListOfFiles")
    var listOfFiles: ArrayList<ListOfFile>? = ArrayList<ListOfFile>()
    @SerializedName("LocationAddress")
    var locationAddress: String? = ""
    @SerializedName("LocationLatitude")
    var locationLatitude: Double? = null
    @SerializedName("LocationLongitude")
    var locationLongitude: Double? = null
    @SerializedName("Price")
    var price: String? = ""
    @SerializedName("ItemCategoryName")
    var itemCategoryName:String?=""
    @SerializedName("SellItemOption")
    var sellItemOptionName:String?=""
    var DonationAgencyId:Int?=null

}