package ru.rdb.db.conf;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = {"ru.rdb"})
@EnableMongoRepositories(basePackages = {"ru.rdb"})
@EnableMongoAuditing
public class Mongo extends AbstractMongoConfiguration {

    @Value("${db.mongourl}")
    private String mongoUrl;

    @Value("${db.dbname}")
    private String dbName;

    @Override
    public MongoClient mongoClient() {
        MongoClientURI uri = new MongoClientURI(mongoUrl);
        return new MongoClient(uri);
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }
}
