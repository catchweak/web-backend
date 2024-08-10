package catchweak.web.es.repository

import catchweak.web.es.dao.ArticleDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ArticleDocumentRepository : ElasticsearchRepository<ArticleDocument, Long> {
    fun findByHeadlineContaining(keyword: String): List<ArticleDocument>

    fun findByAuthor(author: String): List<ArticleDocument>
}
