package com.example.primohomepage.domain

import com.example.primohomepage.domain.model.ArticleModel
import com.example.primohomepage.domain.model.PrimoInfoModel
import javax.inject.Inject

class GetArticleFeedUseCase @Inject constructor(
    private val repository: ArticleFeedsRepository,
) {
    suspend operator fun invoke(): Pair<PrimoInfoModel, List<ArticleModel>> {
        return repository.getFeeds().getOrThrow()
    }
}
