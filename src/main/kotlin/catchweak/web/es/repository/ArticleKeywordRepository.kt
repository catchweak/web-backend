package catchweak.web.es.repository

import catchweak.web.es.dao.ArticleKeywordDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ArticleKeywordRepository : ElasticsearchRepository<ArticleKeywordDocument, Long>{
    fun findByKeywordsContaining(keyword: String, pageable: Pageable): Page<ArticleKeywordDocument>
}
