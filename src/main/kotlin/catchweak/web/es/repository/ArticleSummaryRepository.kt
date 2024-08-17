package catchweak.web.es.repository

import catchweak.web.es.dao.ArticleSummaryDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ArticleSummaryRepository : ElasticsearchRepository<ArticleSummaryDocument, Long>
