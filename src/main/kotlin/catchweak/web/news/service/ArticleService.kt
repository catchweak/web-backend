package catchweak.web.news.service

import catchweak.web.news.dao.Article
import catchweak.web.news.repository.ArticleRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ArticleService(private val articleRepository: ArticleRepository) {

    fun getAllArticles(): List<Article> {
        return articleRepository.findAllWithCategoriesAndSite()
    }

    fun getArticleById(id: Long): Optional<Article> {
        return articleRepository.findById(id)
    }

    @Transactional(readOnly = true)
    fun getHeadlines(): List<Article> {
        // 임시로 최근에 생성된 5개의 기사를 헤드라인으로 반환
        val pageable = PageRequest.of(0, 5)
        val articles: List<Article> = articleRepository.findAllByOrderByArticleCreatedAtDesc(pageable)
        articles.forEach { article -> article.categories.size }

        return articles
    }


    fun getArticlesByCategory(categoryCode: Long, pageable: Pageable): Page<Article> {
        return articleRepository.findByCategoryCode(categoryCode, pageable)
    }

    fun getArticlesByParentCategory(categoryCode: Long, pageable: Pageable): Page<Article> {
        return articleRepository.findByParentCategoryCode(categoryCode, pageable)
    }

    fun getUnProcessedArticles(): List<Article> {
        return articleRepository.findUnprocessedArticles()
    }

    fun saveArticle(article: Article): Article {
        return articleRepository.save(article)
    }
}
