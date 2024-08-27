package catchweak.web.news.repository

import catchweak.web.news.dao.ArticleComment
import catchweak.web.news.dao.ArticleCommentReply
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleCommentReplyRepository: JpaRepository<ArticleCommentReply, Long> {
    fun findByParentCommentOrderByCreatedAt(parentComment: ArticleComment, pageable: Pageable): Page<ArticleCommentReply>

    fun findByParentCommentAndDeletedFalseOrderByCreatedAt(parentComment: ArticleComment, pageable: Pageable): Page<ArticleCommentReply>

    fun countByParentComment(parentComment: ArticleComment): Int

    fun countByParentCommentAndDeletedFalse(parentComment: ArticleComment): Int

    fun findByParentCommentAndDeletedFalse(parentComment: ArticleComment): List<ArticleCommentReply>
}
