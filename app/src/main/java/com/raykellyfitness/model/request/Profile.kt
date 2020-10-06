package com.raykellyfitness.model.request
import com.google.gson.annotations.SerializedName


class RequestUpdateSalesProfile(
    var customerId: Int? = 0,
    var salesProfile_AddressLine: String? = "",
    var salesProfile_CityId: Int? = 0,
    var salesProfile_City: String? = "",
    var salesProfile_Contact: String? = "",
    var salesProfile_Country: String? = "",
    var salesProfile_CountryId: Int? = 0,
    var salesProfile_Email: String? = "",
    var salesProfile_Name: String? = "",
    var salesProfile_Postcode: String? = "",
    var salesProfile_ProfilePic: String? = "",
    var salesProfile_State: String? = "",
    var salesProfile_StateId: Int? = 0,
    var salesProfile_Suburb: String? = "",
    var hasBankAccountAdded: Boolean? = false
)
class RequestGetProfile(
    var UserId: Int? = 0
)
class RequestUpdateProfile(
    var addressLine: String? = "",
    var age: Int? = 18,
    var cityId: Int? = 0,
    var city: String? = "",
    var contact: String? = "",
    var countryId: Int? = 0,
    var country: String? = "",
    var customerId: Int? = 0,
    var customerName: String? = "",
    var email: String? = "",
    var gender: Int? = 0,
    var logoProfilePic: String? = "",
    var postcode: String? = "",
    var stateId: Int? = 0,
    var state: String? = "",
    var suburb: String? = ""
)
class ResponseGetProfile(
    var addressLine: String? = "",
    var age: Int? = 0,
    var cityId: Int? = 0,
    var contact: String? = "",
    var countryId: Int? = 0,
    var customerId: Int? = 0,
    var customerName: String? = "",
    var email: String? = "",
    var gender: Int? = 0,
    var logoProfilePic: String? = "",
    var postcode: String? = "",
    var stateId: Int? = 0,
    var suburb: String? = ""
)
class ResponseGetSalesProfile(
    var customerId: Int? = 0,
    var salesProfile_AddressLine: String? = "",
    var salesProfile_CityId: Int? = 0,
    var salesProfile_City: String? = "",
    var salesProfile_Contact: String? = "",
    var salesProfile_CountryId: Int? = 0,
    var salesProfile_Country: String? = "",
    var salesProfile_Email: String? = "",
    var salesProfile_Name: String? = "",
    var salesProfile_Postcode: String? = "",
    var salesProfile_ProfilePic: String? = null,
    var salesProfile_StateId: Int? = 0,
    var salesProfile_State: String? = "",
    var salesProfile_Suburb: String? = "",
    var hasBankAccountAdded: Boolean? = false
)
class ResponseCountryStateCity(
    @SerializedName("cityList")
    var listCity: List<City?>? = listOf(),
    @SerializedName("countryList")
    var listCountry: List<Country?>? = listOf(),
    @SerializedName("stateList")
    var listState: List<State?>? = listOf()
) {
    class City(
        var countryId: Int? = 0,
        var id: Int? = 0,
        var name: String? = "",
        var stateId: Int? = 0
    )

    class Country(
        var id: Int? = 0,
        var name: String? = ""
    )

    class State(
        var countryId: Int? = 0,
        var id: Int? = 0,
        var name: String? = ""
    )
}
