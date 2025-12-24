package com.example.primohomepage.data

import com.example.primohomepage.data.model.PrimoFeedDataResponse
import retrofit2.Retrofit
import javax.inject.Inject

class PrimoFeedsRemoteDatasource @Inject constructor(
    private val service: PrimoFeedsService,
) {
    suspend fun getFeeds(): PrimoFeedDataResponse {
        return service.getFeed()
    }
}