package com.raykellyfitness.model.request

import com.google.gson.annotations.SerializedName


data class RequestSellItem(
    @SerializedName("SellItemId")
    var SellItemId: Int? =null,
    var OrderId: Int? =null,
    var OrderStatus: Int? =null
)