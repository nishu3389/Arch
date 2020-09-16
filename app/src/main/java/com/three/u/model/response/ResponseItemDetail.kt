package com.three.u.model.response

import com.google.gson.annotations.SerializedName
import com.three.u.model.request.ListOfFile
import java.io.Serializable
import java.util.*

class ResponseItemDetail :Serializable{
    @SerializedName("customerContact")
    var customerContact: String? = ""
    @SerializedName("customerId")
    var customerId: Long? = null
    @SerializedName("customerName")
    var customerName: String? = ""
    @SerializedName("customerProfilePic")
    var customerProfilePic: String? = ""
    @SerializedName("description")
    var description: String? = ""
    @SerializedName("itemCategory")
    var itemCategory: String? = ""
    @SerializedName("itemCategoryID")
    var itemCategoryID: Long? = null
    @SerializedName("itemName")
    var itemName: String? = null
    @SerializedName("locationAddress")
    var locationAddress: String? = ""
    @SerializedName("locationLatitude")
    var locationLatitude: Double? = null
    @SerializedName("locationLongitude")
    var locationLongitude: Double? = null
    @SerializedName("price")
    var price: String? = ""
    @SerializedName("sellItemId")
    var sellItemId: Long? = null
    @SerializedName("sellOrGiveAway")
    var sellOrGiveAway: Long? = null
    var itemFor: Int? = null

    @SerializedName("listOfFiles")
    var listOfFiles: ArrayList<ListOfFile>? = ArrayList<ListOfFile>()

    @SerializedName("customerEmail")
    var customerEmail:String?=""

    var customerAddressLine:String?=""
    var customerSuburb:String?=""
    var customerCityName:String?=""
    var customerStateName:String?=""
    var customerCountryName:String?=""
    var customerPostcode:String?=""

}