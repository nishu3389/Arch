package com.raykellyfitness.model.request

data class SellItemListRequest(
    var SearchItemName: String = ""
)
data class RequestCancelOrder(
    var OrderId: Int = 0,
    var CancelReason: String = ""
)
