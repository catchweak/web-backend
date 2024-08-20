package catchweak.web.es.repository

import catchweak.web.es.dao.ArticleDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ArticleDocumentRepository : ElasticsearchRepository<ArticleDocument, Long> {
    fun findByHeadlineContaining(keyword: String): List<ArticleDocument>

    fun findByAuthor(author: String): List<ArticleDocument>

    @Query("""
    {
      "nested": {
        "path": "categories",
        "query": {
          "bool": {
            "must": [
              { "term": { "categories.code": "?0" } }
            ]
          }
        }
      }
    }
    """)
    fun findByCategoryCode(categoryCode: String, pageable: Pageable): Page<ArticleDocument>

    @Query("""
    {
      "nested": {
        "path": "categories",
        "query": {
          "bool": {
            "must": [
              { "term": { "categories.parentCode": "?0" } }
            ]
          }
        }
      }
    }
    """)
    fun findByParentCategoryCode(parentCode: String, pageable: Pageable): Page<ArticleDocument>
}
