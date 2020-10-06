package com.raykellyfitness.model.request

import com.google.gson.annotations.SerializedName

class RequestBrowseItem {
    @SerializedName("IsForSell")
    var isForSell: Int? = null
    @SerializedName("ItemCategoryId")
    var itemCategoryId: Int? = null
    @SerializedName("itemCategoryName")
    var itemCategoryName:String?=""
    @SerializedName("ForSellName")
    var forSellName: String? =""
    var PriceRangeId: Int? =0
}