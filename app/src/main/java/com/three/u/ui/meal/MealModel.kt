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
    var image: String = "",
    var url: String = "http://lorempixel.com/800/400/",
    var week: String = "Week 01",
    var title: String = "Pumpkin Soup",
    var desc: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries.",
    var type: String = "image",
    var date: String = "Sunday 13 sep. 2020"
)
