package catchweak.web.es.service

import catchweak.web.es.dao.ArticleDocument
import catchweak.web.es.repository.ArticleDocumentRepository
import catchweak.web.es.repository.ArticleKeywordRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val articleDocumentRepository: ArticleDocumentRepository,
    private val articleKeywordRepository: ArticleKeywordRepository
    ) {
    fun searchByHeadline(keyword: String): List<ArticleDocument> =
        articleDocumentRepository.findByHeadlineContaining(keyword)

    fun searchByAuthor(author: String): List<ArticleDocument> =
        articleDocumentRepository.findByAuthor(author)

    fun getArticlesByCategory(categoryCode: Long, pageable: Pageable): Page<ArticleDocument> {
        val categoryCodeString = categoryCode.toString()

        return if (categoryCode in 100..199) {
            articleDocumentRepository.findByParentCategoryCode(categoryCodeString, pageable)
        } else {
            articleDocumentRepository.findByCategoryCode(categoryCodeString, pageable)
        }
    }

    fun searchByKeyword(keyword: String, pageable: Pageable): List<ArticleDocument> {
        val articleIds = articleKeywordRepository.findByKeywordsContaining(keyword, pageable).content.map {it.articleId}

        return articleDocumentRepository.findAllById(articleIds).toList()
    }
}
