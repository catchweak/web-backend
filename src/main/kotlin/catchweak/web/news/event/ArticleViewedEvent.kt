package catchweak.web.news.event

class ArticleViewedEvent(source: Any, userId: Long, articleId: Long) :
    ArticleUserActionEvent(source, userId, articleId)
