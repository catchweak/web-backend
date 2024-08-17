package catchweak.web.es.dao

import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "articles")
data class ArticleDocument(
    val id: Long,
    @Field(type = FieldType.Nested, includeInParent = true)
    val categories: List<CategoryDocument>? = null,
    val url: String? = null,
    val originUrl: String? = null,
    val headline: String? = null,
    val body: String? = null,
    val imgUrl: String? = null,
    val summary: String? = null,
    val author: String? = null,
    val articleCreatedAt: String? = null,
    val articleUpdatedAt: String? = null,
    val collectedAt: String? = null
)
