package catchweak.web.news.repository

import catchweak.web.news.dao.Article
import catchweak.web.news.dao.ArticleComment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleCommentRepository: JpaRepository<ArticleComment, Long> {
    fun findByArticleOrderByCreatedAtDesc(article: Article, pageable: Pageable): Page<ArticleComment>
}
