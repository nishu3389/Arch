package com.three.u.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class RequestAddBankAccount(
    @SerializedName("bank_account_number")
    var bankAccountNumber: String? = "",
    @SerializedName("country")
    var country: String? = "",
    @SerializedName("currency")
    var currency: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("routing_number")
    var routingNumber: String? = "",
    @SerializedName("userId")
    var userId: String? = "",
    @SerializedName("BankToken")
    var token: String? = ""
)

@Parcelize
class ResponseGetBankSetupDetail(
    var accountHolderName: String? = "",
    var accountNumber: String? = "",
    var bankName: String? = "",
    var bankSetupStatus: Int? = 0,
    var verificationLink: String? = ""
) : Parcelable

class BankStatus() {
    companion object{
        val Init = 1
        val AccountVerificationPending = 2
        val BankAccountRequire = 3
        val Fulfilled = 4
    }
}