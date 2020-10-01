package com.three.u.ui.tipsandtricks

class ResponseTipsOuter : ArrayList<ResponseTipsOuterItem>()

data class ResponseTipsOuterItem(
    val data_list: List<ResponseTipsInner>,
    val day: String,
    var open: Boolean,
    val date: String
)

data class ResponseTipsInner(
    val description: String,
    val id: String,
    val media_url: String,
    val publish_count: String,
    val title: String,
    val updated_at: String
)

data class RequestPosts(
    val type: String = ""
)

data class ResponseTipsDetail(
    val description: String = "",
    var media: List<Media>,
    val title: String = ""
)

data class Media(
    val media_type: String,
    val url: String
)

data class RequestTipsDetail(
    val id: String
)