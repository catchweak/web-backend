package catchweak.web.news.dto

import catchweak.web.news.dao.ArticleCommentReply
import org.springframework.data.domain.Page

data class ArticleCommentReplyListDTO(
    val replies: Page<ArticleCommentReply>,
    val count: Int
)
