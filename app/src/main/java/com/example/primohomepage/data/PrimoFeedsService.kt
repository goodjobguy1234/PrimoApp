package com.example.primohomepage.data

import com.example.primohomepage.data.model.PrimoFeedDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PrimoFeedsService {
    @GET("api.json")
    suspend fun getFeed(
        @Query("rss_url") rssUrl: String = "https://medium.com/feed/@primoapp"
    ): PrimoFeedDataResponse
}