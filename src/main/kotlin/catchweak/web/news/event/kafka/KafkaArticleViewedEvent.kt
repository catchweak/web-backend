package catchweak.web.news.event.kafka

data class KafkaArticleViewedEvent(
    override val articleId: Long,
    override val userId: Long
) : KafkaArticleUserActionEvent()
