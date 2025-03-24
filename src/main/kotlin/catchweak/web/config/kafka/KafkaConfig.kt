package catchweak.web.config.kafka

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaConfig {
    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        return KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092"))
    }

    @Bean
    fun topicArticleCommentedEvents(): NewTopic {
        return NewTopic("catchweak-api.article-commented-event", 1, 1)
    }

    @Bean
    fun topicArticleLikedEvents(): NewTopic {
        return NewTopic("catchweak-api.article-liked-event", 1, 1)
    }

    @Bean
    fun topicArticleViewedEvents(): NewTopic {
        return NewTopic("catchweak-api.article-viewed-event", 1, 1)
    }

    @Bean
    fun topicArticleSharedEvents(): NewTopic {
        return NewTopic("catchweak-api.article-shared-event", 1, 1)
    }
}
