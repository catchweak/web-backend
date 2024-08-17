package catchweak.web.es.service

import catchweak.web.es.dao.ArticleDocument
import catchweak.web.es.repository.ArticleDocumentRepository
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val articleDocumentRepository: ArticleDocumentRepository,
) {
    fun searchByHeadline(keyword: String): List<ArticleDocument> = articleDocumentRepository.findByHeadlineContaining(keyword)

    fun searchByAuthor(author: String): List<ArticleDocument> = articleDocumentRepository.findByAuthor(author)
}
