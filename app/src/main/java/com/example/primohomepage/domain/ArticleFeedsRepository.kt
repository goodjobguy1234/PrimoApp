package com.example.primohomepage.domain

import com.example.primohomepage.data.model.PrimoFeedDataResponse

interface ArticleFeedsRepository {
    suspend fun getFeeds(): Result<PrimoFeedDataResponse>
}