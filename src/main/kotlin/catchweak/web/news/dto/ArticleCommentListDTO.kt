package catchweak.web.news.dto

import catchweak.web.news.dao.ArticleComment
import org.springframework.data.domain.Page

data class ArticleCommentListDTO(
    val comments: Page<ArticleComment>,
    val count: Int
)
