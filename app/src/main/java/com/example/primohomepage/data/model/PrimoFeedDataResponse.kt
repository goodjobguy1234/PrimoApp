package com.example.primohomepage.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PrimoFeedDataResponse(
    val status: String?,
    val feed: FeedDescDataModel?,
    val items: List<FeedItemData>?
)

@JsonClass(generateAdapter = true)
data class FeedItemData(
    val title: String?,
    val pubData: String?,
    val link: String?,
    val author: String?,
    val description: String?,
    val categories: List<String>?
)

@JsonClass(generateAdapter = true)
data class FeedDescDataModel(
    val title: String?,
    val description: String?,
    val image: String?
)

