package catchweak.web.news.event.kafka

import catchweak.web.news.event.status.CommentEventStatus

data class KafkaArticleCommentedEvent(
    override val articleId: Long,
    override val userId: Long,
    val status: CommentEventStatus
) : KafkaArticleUserActionEvent()

