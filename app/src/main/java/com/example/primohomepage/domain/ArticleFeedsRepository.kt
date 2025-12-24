package com.example.primohomepage.domain

import com.example.primohomepage.data.model.PrimoFeedDataResponse
import com.example.primohomepage.domain.model.ArticleModel
import com.example.primohomepage.domain.model.PrimoInfoModel

interface ArticleFeedsRepository {
    suspend fun getFeeds(): Result<Pair<PrimoInfoModel, List<ArticleModel>>>
}