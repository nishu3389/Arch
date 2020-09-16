package com.three.u.model.response

import android.os.Parcelable
import com.three.u.model.request.ListOfFile
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MySellItemItemResponse(
    val isForSell: Int,
    val itemCategoryName: String,
    val itemName: String,
    var itemStatus: Int,
    var itemFor: Int,
    val listOfFiles: List<ListOfFile>,
    val price: String,
    val sellItemId: Int,
    val orderId: Int
) : Parcelable

data class OfFiles(
    val fileName: String,
    val fileURL: String
)

@Parcelize
class MyOrder(
    var itemId: Int? = 0,
    var itemName: String? = "",
    var itemCategory: String? = "",
    var itemPrice: String? = "",
    var listOfImages: List<OfImages?>? = listOf(),
    var orderDate: String? = "",
    var orderId: Int? = 0,
    var orderStatus: Int? = 0,
    var transactionId: String? = ""
) : Parcelable {
    @Parcelize
    class OfImages(
        var fileName: String? = "",
        var fileURL: String? = ""
    ) : Parcelable
}

class MyOrderDetail(
    var addressLine: String? = "",
    var cityId: Int? = 0,
    var cityName: String? = "",
    var contact: String? = "",
    var countryId: Int? = 0,
    var countryName: String? = "",
    var email: String? = "",
    var id: Int? = 0,
    var itemCategory: String? = "",
    var itemDescription: String? = "",
    var itemId: Int? = 0,
    var itemName: String? = "",
    var itemPrice: Double? = 0.0,
    var listOfImages: List<ListOfFile>? = listOf(),
    var name: String? = "",
    var orderDate: String? = "",
    var orderId: Int? = 0,
    var orderStatus: Int? = 0,
    var postcode: String? = "",
    var profilePic: String? = "",
    var shippingAddress: String? = "",
    var shippingCity: String? = "",
    var shippingCityId: Int? = 0,
    var shippingContact: String? = "",
    var shippingCountry: String? = "",
    var shippingCountryId: Int? = 0,
    var shippingEmail: String? = "",
    var shippingName: String? = "",
    var shippingPostCode: String? = "",
    var shippingState: String? = "",
    var shippingStateId: Int? = 0,
    var shippingSuburb: String? = "",
    var stateId: Int? = 0,
    var stateName: String? = "",
    var suburb: String? = "",
    var transactionId: String? = ""
) {
    class OfImages(
        var fileName: String? = "",
        var fileURL: String? = "",
        var id: Int? = 0
    )
}

class RequestMyOrderDetail(
    var OrderId: Int? = 0
)