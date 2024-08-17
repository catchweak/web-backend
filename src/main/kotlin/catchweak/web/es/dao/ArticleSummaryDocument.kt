package catchweak.web.es.dao

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "article_summaries")
data class ArticleSummaryDocument(
    @Id
    val id: Long? = 0,
    val articleId: Long? = null,
    val summary: String? = null,
)
