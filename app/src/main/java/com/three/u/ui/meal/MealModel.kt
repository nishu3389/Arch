package com.three.u.ui.meal

class ResponseMealOuterList : ArrayList<ResponseMealOuter>()

data class ResponseMealOuter(
    var week: String,
    var isOpen: Boolean = false,
    var created_at: String = "2020-09-11"
)

class ResponseMealInnerList : ArrayList<ResponseMealInner>()

data class ResponseMealInner(
    var meal: String,
    var image: String = ""
)
