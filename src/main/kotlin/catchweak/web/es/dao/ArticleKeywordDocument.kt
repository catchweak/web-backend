package catchweak.web.es.dao

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "article_keywords")
data class ArticleKeywordDocument(
    @Id
    val id: Long? = 0,
    val articleId: Long? = null,
    val keywords: String? = null,
)
