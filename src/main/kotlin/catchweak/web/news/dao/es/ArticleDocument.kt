package catchweak.web.news.dao.es

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "articles")
data class ArticleDocument(
    @Id
    val id: Long,
    val categoryId: Long?,
    val categoryName: String?,
    val categoryCode: String?,
    val categoryParentCode: String?,
    val siteId: Long?,
    val siteName: String?,
    val url: String?,
    val originUrl: String?,
    val headline: String?,
    val body: String?,
    val imgUrl: String?,
    val summary: String?,
    val author: String?,
    val articleCreatedAt: String?,
    val articleUpdatedAt: String?,
    val collectedAt: String?,
)
