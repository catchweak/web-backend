package catchweak.web.es.repository

import catchweak.web.es.dao.ArticleKeywordDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ArticleKeywordRepository : ElasticsearchRepository<ArticleKeywordDocument, Long>
