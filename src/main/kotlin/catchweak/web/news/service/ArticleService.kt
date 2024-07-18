package catchweak.web.news.service

import catchweak.web.news.dao.Article
import catchweak.web.news.repository.ArticleRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleService(private val articleRepository: ArticleRepository) {

    fun getAllArticles(): List<Article> {
        return articleRepository.findAllWithCategoryAndSite()
    }

    fun getArticleById(id: Long): Optional<Article> {
        return articleRepository.findById(id)
    }

    fun getHeadlines(): List<Article> {
        // 임시로 최근에 생성된 5개의 기사를 헤드라인으로 반환
        return articleRepository.findAllWithCategoryAndSite().sortedByDescending { it.articleCreatedAt }.take(5)
    }

    fun getArticlesByCategory(categoryCode: String, pageable: Pageable): Page<Article> {
        return articleRepository.findByCategoryCode(categoryCode, pageable)
    }

    fun getUnProcessedArticles(limit: Long): List<Article> {
        return articleRepository.findUnprocessedArticles(limit)
    }

    fun saveArticle(article: Article): Article {
        return articleRepository.save(article)
    }
}
