package com.raykellyfitness.ui.meal

class ResponseMealOuterList : ArrayList<ResponseMealOuter>()

data class ResponseMealOuter(
    var day: String,
    var isOpen: Boolean = false,
    var date: String = "2020-09-11",
    var data : ResponseMealInnerList
)

class ResponseMealInnerList : ArrayList<ResponseMealInner>()

data class ResponseMealInner(
    var id: String,
    var publish_count: String,
    var title: String = "Pumpkin Soup",
    var dateday: String = "Sunday 13 sep. 2020",
    var desc: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries.",
    var type: String = "image"
)
