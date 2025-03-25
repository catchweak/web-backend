package catchweak.web.news.event.kafka

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class ArticleEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, KafkaArticleUserActionEvent>
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun publishCommentedEvent(event: KafkaArticleCommentedEvent) {
        logger.info("publishing kafka commented event: $event")
        kafkaTemplate.send("catchweak-api.article-commented-event", event.articleId.toString(), event)
    }

    fun publishLikedEvent(event: KafkaArticleLikedEvent) {
        logger.info("publishing kafka liked event: $event")
        kafkaTemplate.send("catchweak-api.article-liked-event", event.articleId.toString(), event)
    }

    fun publishSharedEvent(event: KafkaArticleSharedEvent) {
        logger.info("publishing kafka shared event: $event")
        kafkaTemplate.send("catchweak-api.article-shared-event", event.articleId.toString(), event)
    }

    fun publishViewedEvent(event: KafkaArticleViewedEvent) {
        logger.info("publishing kafka viewed event: $event")
        kafkaTemplate.send("catchweak-api.article-viewed-event", event.articleId.toString(), event)
    }
}
