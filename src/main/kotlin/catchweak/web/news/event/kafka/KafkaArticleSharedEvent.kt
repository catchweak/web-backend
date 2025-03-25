package catchweak.web.news.event.kafka

data class KafkaArticleSharedEvent(
    override val articleId: Long,
    override val userId: Long
) : KafkaArticleUserActionEvent()
