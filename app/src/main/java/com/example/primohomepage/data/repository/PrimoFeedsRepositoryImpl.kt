package com.example.primohomepage.data.repository

import com.example.primohomepage.data.PrimoFeedsRemoteDatasource
import com.example.primohomepage.data.model.PrimoFeedDataResponse
import com.example.primohomepage.domain.ArticleFeedsRepository
import javax.inject.Inject

class PrimoFeedsRepositoryImpl @Inject constructor(
    private val remoteDatasource: PrimoFeedsRemoteDatasource
): ArticleFeedsRepository {
    override suspend fun getFeeds(): Result<PrimoFeedDataResponse> {
        return runCatching {
            remoteDatasource.getFeeds().toArticleModel()
        }
    }

    private fun PrimoFeedDataResponse.toArticleModel():  {

    }
}