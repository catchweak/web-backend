package catchweak.web.news.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisNewsScoreService(
    private val redisTemplate: StringRedisTemplate,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private final val popularArticlesKey = "popular_articles"

    private val eventScoreMap = mapOf(
        "liked" to 3.5,
        "commented" to 3.5,
        "shared" to 1.5,
        "viewed" to 1.5,
        "unliked" to -3.5,
        "deleteCommented" to -3.5,
    )

    fun updateArticleScore(articleId: Long, eventType: String) {
        val score = eventScoreMap[eventType]?:0.0

        logger.info("Updated score for article $articleId by $score")
        redisTemplate.opsForZSet().incrementScore(popularArticlesKey, articleId.toString(), score)
    }

    fun getTopPopularArticleIds(limit: Long): List<Long> {
        logger.info("Looking up top popular articles for $limit")

        return redisTemplate.opsForZSet()
            .reverseRange(popularArticlesKey, 0, limit - 1)
            ?.map { it.toLong() } ?: emptyList()
    }

    fun getArticleScore(articleId: Long): Double {
        logger.info("Getting score for article $articleId by $popularArticlesKey")

        return redisTemplate.opsForZSet().score(popularArticlesKey, articleId.toString()) ?: 0.0
    }
}
