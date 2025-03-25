package catchweak.web.news.event

class ArticleSharedEvent(source: Any, userId: Long, articleId: Long) :
    ArticleUserActionEvent(source, userId, articleId)
