package catchweak.web.news.event.kafka

import catchweak.web.news.event.status.LikedEventStatus

data class KafkaArticleLikedEvent(
    override val articleId: Long,
    override val userId: Long,
    val status: LikedEventStatus
) : KafkaArticleUserActionEvent()
