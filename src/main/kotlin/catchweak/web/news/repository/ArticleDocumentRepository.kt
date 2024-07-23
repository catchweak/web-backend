package catchweak.web.news.repository

import catchweak.web.news.dao.es.ArticleDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ArticleDocumentRepository : ElasticsearchRepository<ArticleDocument, Long> {
    fun findByHeadlineContaining(keyword: String): List<ArticleDocument>

    fun findByAuthor(author: String): List<ArticleDocument>
}
