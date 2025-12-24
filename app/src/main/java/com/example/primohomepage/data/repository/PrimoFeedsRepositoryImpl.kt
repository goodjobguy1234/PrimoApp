package com.example.primohomepage.data.repository

import com.example.primohomepage.data.PrimoFeedDao
import com.example.primohomepage.data.PrimoFeedsRemoteDatasource
import com.example.primohomepage.data.entity.PrimoFeedEntity
import com.example.primohomepage.data.extension.toJson
import com.example.primohomepage.data.extension.toListObject
import com.example.primohomepage.data.extension.toObject
import com.example.primohomepage.data.model.FeedDescDataModel
import com.example.primohomepage.data.model.FeedItemData
import com.example.primohomepage.domain.ArticleFeedsRepository
import com.example.primohomepage.domain.model.ArticleModel
import com.example.primohomepage.domain.model.PrimoInfoModel
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID
import javax.inject.Inject

class PrimoFeedsRepositoryImpl @Inject constructor(
    private val remoteDatasource: PrimoFeedsRemoteDatasource,
    private val localDatasource: PrimoFeedDao,
    private val moshi: Moshi
) : ArticleFeedsRepository {
    override suspend fun getFeeds(): Result<Pair<PrimoInfoModel, List<ArticleModel>>> {
        return runCatching {
            getFeedFromDatabase()?.let { domainModel ->
                if (domainModel.second.isEmpty()) {
                    null
                } else {
                    domainModel
                }
            } ?: getFeedFromRemote()
        }
    }

    private suspend fun getFeedFromDatabase() = localDatasource.getPrimoFeeds().firstOrNull()?.toDomainModel()

    private suspend fun getFeedFromRemote() = remoteDatasource.getFeeds().let {
            // save to local
            val primoInfo = it.feed?.toPrimoInfoModel() ?: PrimoInfoModel(
                title = "",
                desc = "",
                image = ""
            )
            val primoArticle = it.items?.toArticlesModel() ?: emptyList()
            return@let (primoInfo to primoArticle).also {
                val entity = mapToEntity(
                    primoInfoModel = it.first,
                    articlesModel = it.second
                )
                localDatasource.clearAndInsert(entity)
            }
        }


    private fun mapToEntity(
        primoInfoModel: PrimoInfoModel,
        articlesModel: List<ArticleModel>
    ): PrimoFeedEntity {
        return PrimoFeedEntity(
            uuid = UUID.randomUUID().toString(),
            feedInfo = primoInfoModel.toJson<PrimoInfoModel>(moshi),
            articleList = articlesModel.toJson<ArticleModel>(moshi)
        )
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
                dateString = model.pubData ?: "",
                thumbnail = extractThumbnail(model.description ?: ""),
                descriptionHtmlText = extractOnlyText(model.description ?: ""),
                categories = model.categories ?: emptyList(),
                detailLink = model.link ?: ""
            )
        } ?: emptyList()
    }

    private fun FeedDescDataModel?.toPrimoInfoModel() = PrimoInfoModel(
        title = this?.title ?: "",
        desc = this?.description ?: "",
        image = this?.image ?: ""
    )

    private fun PrimoFeedEntity?.toDomainModel(): Pair<PrimoInfoModel, List<ArticleModel>> {
        val articleEntityToList =
            this?.articleList?.toListObject<ArticleModel>(moshi) ?: emptyList()
        val primoInfo = this?.feedInfo?.toObject<PrimoInfoModel>(moshi) ?: PrimoInfoModel(
            title = "",
            desc = "",
            image = ""
        )
        return primoInfo to articleEntityToList
    }
}
