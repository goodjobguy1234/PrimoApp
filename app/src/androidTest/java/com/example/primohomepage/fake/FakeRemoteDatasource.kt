package com.example.primohomepage.fake

import com.example.primohomepage.data.PrimoFeedsRemoteDatasource
import com.example.primohomepage.data.PrimoFeedsService
import com.example.primohomepage.data.model.FeedDescDataModel
import com.example.primohomepage.data.model.PrimoFeedDataResponse
import io.mockk.mockk
import javax.inject.Inject

class FakeRemoteDataSource @Inject constructor(private val service: PrimoFeedsService,) : PrimoFeedsRemoteDatasource(service) {
    override suspend fun getFeeds(): PrimoFeedDataResponse {
        return PrimoFeedDataResponse(
            status = "ok",
            feed = FeedDescDataModel(
                title = "",
                description = "",
                image = ""
            ),
            items = emptyList()
        )
    }
}