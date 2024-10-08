package catchweak.web.news.repository

import catchweak.web.news.dao.Article
import catchweak.web.news.dao.ArticleComment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleCommentRepository: JpaRepository<ArticleComment, Long> {
    fun findAllByArticle(article: Article): List<ArticleComment>

    fun findByArticleOrderByCreatedAtDesc(article: Article, pageable: Pageable): Page<ArticleComment>

    fun findByArticleAndDeletedFalseOrderByCreatedAtDesc(article: Article, pageable: Pageable): Page<ArticleComment>

    fun countByArticle(article: Article): Int

    fun countByArticleAndDeletedFalse(article: Article): Int
}
