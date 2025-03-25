package catchweak.web.news.event

import catchweak.web.news.event.status.LikedEventStatus

class ArticleLikedEvent(source: Any, userId: Long, articleId: Long, val status: LikedEventStatus) :
    ArticleUserActionEvent(source, userId, articleId)
