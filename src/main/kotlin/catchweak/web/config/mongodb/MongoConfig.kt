package catchweak.web.config.mongodb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter

@Configuration
class MongoConfig {

    @Bean
    fun gridFsTemplate(
        mongoDbFactory: MongoDatabaseFactory,
        mongoConverter: MappingMongoConverter
    ): GridFsTemplate {
        return GridFsTemplate(mongoDbFactory, mongoConverter)
    }
}
