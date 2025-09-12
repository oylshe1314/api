package com.sk.op.api.server.config;


import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.PropertiesMongoConnectionDetails;
import org.springframework.boot.autoconfigure.mongo.StandardMongoClientSettingsBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootConfiguration
@EnableMongoRepositories(basePackages = "com.sk.op.api.server.repository"/*, mongoTemplateRef = "adminMongoTemplate"*/)
public class AdminDatasourceConfiguration {

//    @Primary
//    @Bean("adminMongoProperties")
//    @ConfigurationProperties("spring.data.mongodb.admin")
//    public MongoProperties adminMongoProperties() {
//        return new MongoProperties();
//    }
//
//    @Primary
//    @Bean("adminMongoDatabaseFactory")
//    public MongoDatabaseFactory adminMongoDatabaseFactory(@Qualifier("adminMongoProperties") MongoProperties mongoProperties) {
//
//        MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder();
//        PropertiesMongoConnectionDetails connectionDetails = new PropertiesMongoConnectionDetails(mongoProperties, new DefaultSslBundleRegistry());
//        StandardMongoClientSettingsBuilderCustomizer builderCustomizer = new StandardMongoClientSettingsBuilderCustomizer(connectionDetails, UuidRepresentation.STANDARD);
//
//        builderCustomizer.customize(settingsBuilder);
//
//        return new SimpleMongoClientDatabaseFactory(MongoClients.create(), mongoProperties.getDatabase());
//    }
//
//    @Primary
//    @Bean("adminMongoTemplate")
//    public MongoTemplate adminMongoTemplate(@Qualifier("adminMongoDatabaseFactory") MongoDatabaseFactory gameMongoDatabaseFactory) {
//        return new MongoTemplate(gameMongoDatabaseFactory);
//    }
}
