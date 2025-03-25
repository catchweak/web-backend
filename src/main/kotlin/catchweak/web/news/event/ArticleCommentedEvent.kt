package catchweak.web.news.event

import catchweak.web.news.event.status.CommentEventStatus

class ArticleCommentedEvent(source: Any, userId: Long, articleId: Long, val status: CommentEventStatus) :
    ArticleUserActionEvent(source, userId, articleId)
