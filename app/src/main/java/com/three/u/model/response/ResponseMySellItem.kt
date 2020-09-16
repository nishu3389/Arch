package com.three.u.model.response

import com.google.gson.annotations.SerializedName
import com.three.u.model.request.ListOfFile
import java.util.*

class ResponseMySellItem {

    @SerializedName("sellItemId")
    var sellItemId: Int? = null

    @SerializedName("description")
    var description: String? = ""

    var donationBodiesId: Int? = null

    @SerializedName("price")
    var price: String? = ""

    var itemFor: Int? = null

    var itemStatus: Int? = 0

    @SerializedName("itemCategoryID")
    var itemCategoryID: Int? = null

    @SerializedName("itemCategory")
    var itemCategory: String? = ""

    @SerializedName("itemName")
    var itemName:String?=""

    @SerializedName("listOfFiles")
    var listOfFiles: ArrayList<ListOfFile>? = ArrayList<ListOfFile>()

    var customerName:String?=""
    var customerProfilePic:String?=""
    var shippingProfilePic:String?=""
    var customerContact:String?=""
    var customerEmail:String?=""
    var customerAddressLine:String?=""
    var customerSuburb:String?=""
    var customerCityId:String?=""
    var customerCityName:String?=""
    var customer_StateId:String?=""
    var customer_StateName:String?=""
    var customer_CountryId:String?=""
    var customer_CountryName:String?=""
    var customer_Postcode:String?=""
    var shippingName:String?=""
    var shippingEmail:String?=""
    var shippingContact:String?=""
    var shippingAddress:String?=""
    var shippingCountry:String?=""
    var shippingState:String?=""
    var shippingCity:String?=""
    var shippingSuburb:String?=""
    var shippingPostCode:String?=""
    var agencyName:String?=""
    var contact:String?=""
    var address:String?=""
    var emailAddress:String?=""
}