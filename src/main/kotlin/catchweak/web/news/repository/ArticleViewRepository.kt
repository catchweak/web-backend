package catchweak.web.news.repository

import catchweak.web.member.model.entity.Member
import catchweak.web.news.dao.Article
import catchweak.web.news.dao.ArticleView
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleViewRepository: JpaRepository<ArticleView, Long> {
    fun findByArticleAndUser(article: Article, user: Member): ArticleView?
}
