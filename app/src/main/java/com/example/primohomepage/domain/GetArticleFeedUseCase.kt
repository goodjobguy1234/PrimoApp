package com.example.primohomepage.domain

import javax.inject.Inject

//todo do use case later for domain layer
class GetArticleFeedUseCase @Inject constructor(
    private val repository: ArticleFeedsRepository
) {
    suspend operator fun invoke() {
        // todo create domain data show here for showing at view
        repository.getFeeds()
    }
}