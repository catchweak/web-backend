package catchweak.web.news.service

import catchweak.web.news.dao.es.ArticleDocument
import catchweak.web.news.repository.ArticleDocumentRepository
import org.springframework.stereotype.Service

@Service
class ArticleSearchService(
    private val articleDocumentRepository: ArticleDocumentRepository,
) {
    fun searchByHeadline(keyword: String): List<ArticleDocument> = articleDocumentRepository.findByHeadlineContaining(keyword)

    fun searchByAuthor(author: String): List<ArticleDocument> = articleDocumentRepository.findByAuthor(author)
}
