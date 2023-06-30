package com.depromeet.whatnow.config.mongo

import com.depromeet.whatnow.config.MongoDBProperties
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter

@Configuration
class MongoConfig(
    val mongoDBProperties: MongoDBProperties,
) : AbstractReactiveMongoConfiguration() {
    var mongoProperties: MongoDBProperties.MongoDB = mongoDBProperties.mongodb

    override fun getDatabaseName(): String {
        return mongoProperties.database
    }
    override fun reactiveMongoClient(): com.mongodb.reactivestreams.client.MongoClient {
        return MongoClients.create(mongoProperties.url)
    }

    @Bean("mongoTemplate")
    override fun reactiveMongoTemplate(databaseFactory: ReactiveMongoDatabaseFactory, mongoConverter: MappingMongoConverter): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoClient(), databaseName)
    }
}
