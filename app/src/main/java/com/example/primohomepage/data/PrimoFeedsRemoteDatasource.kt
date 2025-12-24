package com.example.primohomepage.data

import com.example.primohomepage.data.model.PrimoFeedDataResponse
import javax.inject.Inject

open class PrimoFeedsRemoteDatasource @Inject constructor(
    private val service: PrimoFeedsService,
) {
    open suspend fun getFeeds(): PrimoFeedDataResponse {
        return service.getFeed()
    }
}