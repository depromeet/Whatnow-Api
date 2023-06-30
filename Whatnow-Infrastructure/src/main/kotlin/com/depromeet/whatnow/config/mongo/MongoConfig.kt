package com.depromeet.whatnow.config.mongo

import com.depromeet.whatnow.config.MongoDBProperties
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate

@Configuration
class MongoConfig(
    val mongoDBProperties: MongoDBProperties,
): AbstractReactiveMongoConfiguration() {
    var mongoProperties: MongoDBProperties.MongoDB = mongoDBProperties.mongodb

    override fun getDatabaseName(): String {
        return mongoProperties.database
    }
    override fun reactiveMongoClient(): com.mongodb.reactivestreams.client.MongoClient {
        return MongoClients.create(mongoProperties.url)
    }
    override fun reactiveMongoTemplate(databaseFactory: ReactiveMongoDatabaseFactory, mongoConverter: MappingMongoConverter): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(reactiveMongoClient(), databaseName)
    }
}