package catchweak.web.news.event

import catchweak.web.news.event.kafka.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ArticleUserActionHandler(private val articleEventProducer: ArticleEventProducer) {

    private val logger = LoggerFactory.getLogger(ArticleUserActionHandler::class.java)

    @TransactionalEventListener
    fun handleCommentedEvent(event: ArticleCommentedEvent) {
        logger.info("handle news commented event: $event")
        articleEventProducer.publishCommentedEvent(KafkaArticleCommentedEvent(event.articleId, event.userId, event.status))
    }

    @TransactionalEventListener
    fun handleLikedEvent(event: ArticleLikedEvent) {
        logger.info("handle news liked event: $event")
        articleEventProducer.publishLikedEvent(KafkaArticleLikedEvent(event.articleId, event.userId, event.status))
    }

    @TransactionalEventListener
    fun handleViewedEvent(event: ArticleViewedEvent) {
        logger.info("handle news viewed event: $event")
        articleEventProducer.publishViewedEvent(KafkaArticleViewedEvent(event.articleId, event.userId))
    }

    @TransactionalEventListener
    fun handleSharedEvent(event: ArticleSharedEvent) {
        logger.info("handle news shared event: $event")
        articleEventProducer.publishSharedEvent(KafkaArticleSharedEvent(event.articleId, event.userId))
    }
}
