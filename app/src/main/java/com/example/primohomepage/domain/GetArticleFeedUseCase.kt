package com.example.primohomepage.domain

import com.example.primohomepage.data.model.FeedDescDataModel
import com.example.primohomepage.data.model.FeedItemData
import com.example.primohomepage.domain.model.ArticleModel
import com.example.primohomepage.domain.model.PrimoInfoModel
import javax.inject.Inject

//todo do use case later for domain layer
class GetArticleFeedUseCase @Inject constructor(
    private val repository: ArticleFeedsRepository,
) {
    suspend operator fun invoke(): Pair<PrimoInfoModel, List<ArticleModel>> {
        return repository.getFeeds().getOrThrow().let {
            it.feed.toPrimoInfoModel() to it.items.toArticlesModel()
        }
    }

    private fun extractThumbnail(html: String): String {
        val imgRegex = "src=\"(.*?)\"".toRegex()
        val imageUrl = imgRegex.find(html)?.groups?.get(1)?.value
        return imageUrl ?: ""
    }

    private fun extractOnlyText(html: String): String {
        //remove figure entire block
        val imgRegex = """(?s)<figure.*?>.*?</figure>""".toRegex()
        return html.replace(imgRegex, "")
    }

    private fun List<FeedItemData>?.toArticlesModel(): List<ArticleModel> {
        return this?.map { model ->
            ArticleModel(
                title = model.title ?: "",
                dateString = model.pubData ?: "", //todo format later
                thumbnail = extractThumbnail(model.description ?: ""),
                descriptionHtmlText = extractOnlyText(model.description ?: ""),
                categories = model.categories ?: emptyList()
            )
        } ?: emptyList()
    }

    private fun FeedDescDataModel?.toPrimoInfoModel() = PrimoInfoModel(
        title = this?.title ?: "",
        desc = this?.description ?: "",
        image = this?.image ?: ""
    )
}
