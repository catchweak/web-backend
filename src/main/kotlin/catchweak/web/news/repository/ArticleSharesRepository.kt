package catchweak.web.news.repository

import catchweak.web.member.model.entity.Member
import catchweak.web.news.dao.Article
import catchweak.web.news.dao.ArticleShares
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleSharesRepository : JpaRepository<ArticleShares, Long> {
    fun findByArticleAndUser(article: Article, user: Member): ArticleShares?
}
