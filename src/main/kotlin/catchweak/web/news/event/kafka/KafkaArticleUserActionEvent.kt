package catchweak.web.news.event.kafka

sealed class KafkaArticleUserActionEvent {
    abstract val articleId: Long
    abstract val userId: Long
}
