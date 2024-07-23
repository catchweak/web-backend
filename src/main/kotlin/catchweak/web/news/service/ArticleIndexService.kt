package catchweak.web.news.service

import catchweak.web.news.dao.es.ArticleDocument
import catchweak.web.news.dao.es.CategoryDocument
import catchweak.web.news.repository.ArticleDocumentRepository
import catchweak.web.news.repository.ArticleRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ArticleIndexService(
    private val articleRepository: ArticleRepository,
    private val articleDocumentRepository: ArticleDocumentRepository,
) {
    @Transactional
    fun indexAllArticles() {
        var page = 0
        val pageSize = 100

        while (true) {
            val pageable = PageRequest.of(page, pageSize)
            val articlePage = articleRepository.findAll(pageable)

            if (articlePage.isEmpty) {
                break
            }

            val articleDocuments = articlePage.content.map { article ->
                ArticleDocument(
                    id = article.id,
                    categories = article.categories.map { category ->
                        CategoryDocument(
                            id = category.id,
                            name = category.name,
                            code = category.code,
                            parentCode = category.parentCode,
                            siteId = category.site?.id,
                            siteName = category.site?.name
                        )
                    },
                    url = article.url,
                    originUrl = article.originUrl,
                    headline = article.headline,
                    body = article.body,
                    imgUrl = article.imgUrl,
                    summary = article.summary,
                    author = article.author,
                    articleCreatedAt = article.articleCreatedAt,
                    articleUpdatedAt = article.articleUpdatedAt,
                    collectedAt = article.collectedAt?.toString()
                )
            }

            articleDocumentRepository.saveAll(articleDocuments)
            page++
        }
    }
}

