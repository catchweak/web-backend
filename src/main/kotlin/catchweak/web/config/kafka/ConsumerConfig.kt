package catchweak.web.config.kafka

import catchweak.web.news.event.kafka.KafkaArticleUserActionEvent

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class ConsumerConfig {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, KafkaArticleUserActionEvent> {
        val deserializer = JsonDeserializer(KafkaArticleUserActionEvent::class.java)
        deserializer.addTrustedPackages("*")

        val config = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "127.0.0.1:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "popular-news-group",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to deserializer::class.java.name,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
        )

        return DefaultKafkaConsumerFactory(config, StringDeserializer(), deserializer)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, KafkaArticleUserActionEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, KafkaArticleUserActionEvent>()
        factory.consumerFactory = consumerFactory()
        factory.isBatchListener = false
        factory.setConcurrency(1)

        return factory
    }
}
