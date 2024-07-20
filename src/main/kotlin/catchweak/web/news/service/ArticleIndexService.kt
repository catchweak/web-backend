package catchweak.web.news.service

import catchweak.web.news.dao.es.ArticleDocument
import catchweak.web.news.repository.ArticleDocumentRepository
import catchweak.web.news.repository.ArticleRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ArticleIndexService(
    private val articleRepository: ArticleRepository,
    private val articleDocumentRepository: ArticleDocumentRepository,
) {
    @Transactional
    fun indexAllArticles() {
        val articles = articleRepository.findAll()
        val articleDocuments =
            articles.map { article ->
                ArticleDocument(
                    id = article.id,
                    categoryId = article.category?.id,
                    categoryName = article.category?.name,
                    categoryCode = article.category?.code,
                    categoryParentCode = article.category?.parentCode,
                    siteId = article.category?.site?.id,
                    siteName = article.category?.site?.name,
                    url = article.url,
                    originUrl = article.originUrl,
                    headline = article.headline,
                    body = article.body,
                    imgUrl = article.imgUrl,
                    summary = article.summary,
                    author = article.author,
                    articleCreatedAt = article.articleCreatedAt,
                    articleUpdatedAt = article.articleUpdatedAt,
                    collectedAt = article.collectedAt?.toString(),
                )
            }
        articleDocumentRepository.saveAll(articleDocuments)
    }
}
