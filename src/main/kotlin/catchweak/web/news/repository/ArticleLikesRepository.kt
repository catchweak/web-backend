package catchweak.web.news.repository

import catchweak.web.member.model.entity.Member
import catchweak.web.news.dao.Article
import catchweak.web.news.dao.ArticleLikes
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleLikesRepository : JpaRepository<ArticleLikes, Long> {
    fun findByArticleAndUser(article: Article, user: Member): ArticleLikes?
}
