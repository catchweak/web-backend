package catchweak.web.news.event.kafka

import catchweak.web.news.event.status.CommentEventStatus
import catchweak.web.news.event.status.LikedEventStatus
import catchweak.web.news.service.RedisNewsScoreService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ArticleEventConsumer(
    private val redisNewsScoreService: RedisNewsScoreService,
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["catchweak-api.article-commented-event"],
        groupId = "popular-news-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun handleCommentedEvent(event: KafkaArticleCommentedEvent) {
        logger.info("Received kafka commented event: $event")

        when (event.status) {
            CommentEventStatus.CREATED -> {
                redisNewsScoreService.updateArticleScore(event.articleId, "commented")
            }
            CommentEventStatus.DELETED -> {
                redisNewsScoreService.updateArticleScore(event.articleId, "deleteCommented")
            }
        }
    }

    @KafkaListener(
        topics = ["catchweak-api.article-liked-event"],
        groupId = "popular-news-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun handleLikedEvent(event: KafkaArticleLikedEvent) {
        logger.info("Received kafka liked event: $event")

        when(event.status){
            LikedEventStatus.COMPLETED -> {
                redisNewsScoreService.updateArticleScore(event.articleId, "liked")
            }
            LikedEventStatus.CANCELED -> {
                redisNewsScoreService.updateArticleScore(event.articleId, "unliked")
            }
        }
    }

    @KafkaListener(
        topics = ["catchweak-api.article-shared-event"],
        groupId = "popular-news-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun handleSharedEvent(event: KafkaArticleSharedEvent) {
        logger.info("Received kafka shared event: $event")

        redisNewsScoreService.updateArticleScore(event.articleId, "shared")
    }

    @KafkaListener(
        topics = ["catchweak-api.article-viewed-event"],
        groupId = "popular-news-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun handleViewedEvent(event: KafkaArticleViewedEvent) {
        logger.info("Received kafka viewed event: $event")

        redisNewsScoreService.updateArticleScore(event.articleId, "viewed")
    }
}
