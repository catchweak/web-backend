package catchweak.web.es.service

import catchweak.web.es.dao.ArticleDocument
import catchweak.web.es.dao.ArticleKeywordDocument
import catchweak.web.es.dao.ArticleSummaryDocument
import catchweak.web.es.dao.CategoryDocument
import catchweak.web.es.repository.ArticleDocumentRepository
import catchweak.web.es.repository.ArticleKeywordRepository
import catchweak.web.es.repository.ArticleSummaryRepository
import catchweak.web.morpheme.repository.MorphemeAnalysisResultRepository
import catchweak.web.news.repository.ArticleRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class IndexService(
    private val articleRepository: ArticleRepository,
    private val articleDocumentRepository: ArticleDocumentRepository,
    private val morphemeAnalysisResultRepository: MorphemeAnalysisResultRepository,
    private val articleKeywordRepository: ArticleKeywordRepository,
    private val articleSummaryRepository: ArticleSummaryRepository,
) {

    fun indexArticleKeywordsAndSummaries() {
        var page = 0
        val pageSize = 100
        while (true) {
            val pageable = PageRequest.of(page, pageSize)
            val morphemeAnalysisPage = morphemeAnalysisResultRepository.findAll(pageable)

            if (morphemeAnalysisPage.isEmpty) {
                break
            }

            val keywordDocuments = morphemeAnalysisPage.content.map { doc ->
                ArticleKeywordDocument(
                    id = doc.id,
                    articleId = doc.articleId,
                    keywords = doc.keywords
                )
            }

            val summaryDocuments = morphemeAnalysisPage.content.map { doc ->
                ArticleSummaryDocument(
                    id = doc.id,
                    articleId = doc.articleId,
                    summary = doc.summary
                )
            }

            articleKeywordRepository.saveAll(keywordDocuments)
            articleSummaryRepository.saveAll(summaryDocuments)
            page++
        }
    }

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

