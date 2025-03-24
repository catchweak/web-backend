package catchweak.web.config.redis

import catchweak.web.morpheme.dto.KeywordFrequency
import catchweak.web.news.dto.PopularArticleDTO
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisStartupInitializer {

    @Bean
    fun redisInitializer(redisTemplate: RedisTemplate<String, Any>): CommandLineRunner {
        return CommandLineRunner {
            val defaultKeys = mapOf(
                "hot_topics" to mutableListOf<KeywordFrequency>(),
            )

            defaultKeys.forEach { (key, value) ->
                if (redisTemplate.opsForValue().get(key) == null) {
                    redisTemplate.opsForValue().set(key, value)
                }
            }

            val defaultZSetKeys = listOf("popular_articles")

            defaultZSetKeys.forEach { key ->
                if (!redisTemplate.hasKey(key)) {
                    redisTemplate.opsForZSet()
                }
            }
        }
    }
}
