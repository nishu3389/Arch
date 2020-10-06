package com.raykellyfitness.model.response

class AdvlistResponse(
    var adList: List<AdvModel?>? = listOf(),
    var businessCount: Int? = 0,
    var residentialCount: Int? = 0
)

class AdvModel(
    var advertisementId: Int? = 0,
    var advertisementImage: String? = "",
    var advertisementType: Int? = 0,
    var advertisementURL: String? = ""
)

open class Adv {
    companion object{
        var Mobile_MREC = 4 // large
        var Mobile_PopUp = 5 // extra large
        var Mobile_Banner = 6 // small
        var Mobile_BillBoard = 7 // Medium
    }
}

open class AdvSection {
    companion object{
        var NONE = 0
        var HomePage = 1
        var Checklist = 2
        var AddBudget = 3
        var AddExpenses = 4
        var Share = 5
        var Verification = 6
        var AddChecklist = 7
        var MyItems = 8
        var U3Market = 9
        var ServiceDirectory = 10
        var U3Agent = 11
        var RedeemFreeBox = 12
        var Congratulations = 13
    }
}

