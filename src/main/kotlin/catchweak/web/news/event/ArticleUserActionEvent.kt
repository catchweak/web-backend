package catchweak.web.news.event

import org.springframework.context.ApplicationEvent

sealed class ArticleUserActionEvent(
    source: Any,
    val userId: Long,
    val articleId: Long,
) : ApplicationEvent(source)
