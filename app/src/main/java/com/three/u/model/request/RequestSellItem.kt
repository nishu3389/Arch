package com.three.u.model.request

import com.google.gson.annotations.SerializedName
import java.util.ArrayList


data class RequestSellItem(
    @SerializedName("SellItemId")
    var SellItemId: Int? =null,
    var OrderId: Int? =null,
    var OrderStatus: Int? =null
)