package catchweak.web.news.repository

import catchweak.web.news.dao.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ArticleRepository : JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.category c LEFT JOIN FETCH c.site")
    fun findAllWithCategoryAndSite(): List<Article>

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.category c LEFT JOIN FETCH c.site WHERE c.code = :categoryCode")
    fun findByCategoryCode(@Param("categoryCode") categoryCode: String, pageable: Pageable): Page<Article>

    @Query("SELECT a FROM Article a WHERE a.processed = false OR a.processed = null ORDER BY a.id LIMIT 300")
    fun findUnprocessedArticles(): List<Article>
}
