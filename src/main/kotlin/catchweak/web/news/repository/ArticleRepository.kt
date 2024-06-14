package catchweak.web.news.repository

import catchweak.web.news.dao.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleRepository : JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.category c LEFT JOIN FETCH c.site")
    fun findAllWithCategoryAndSite(): List<Article>
}
