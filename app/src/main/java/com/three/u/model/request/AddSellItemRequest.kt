package com.three.u.model.request

data class AddSellItemRequest(
    val Description: String = "",
    val IsForSell: Int = 0,
    val ItemCategory: Int = 0,
    val ItemName: String = "",
    val ListOfFiles: List<OfFiles> = listOf(OfFiles()),
    val LocationAddress: String = "",
    val LocationLatitude: Double = 26.5,
    val LocationLongitude: Double = 56.51,
    val Price: Double = 0.0
)

data class OfFiles(
    val Filename: String = ""
)