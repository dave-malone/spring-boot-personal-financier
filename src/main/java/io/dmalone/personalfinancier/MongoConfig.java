package io.dmalone.personalfinancier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.Mongo;

@Configuration
public class MongoConfig {
	
	@Value("${mongo.username:}") private String mongoUsername;
	@Value("${mongo.password:}") private String mongoPassword;
	@Value("${mongo.database:personalfinancier}") private String mongoDatabase;
	@Value("${mongo.host:localhost}") private String mongoHost;
	@Value("${mongo.port:27017}") private int mongoPort;
	
	public @Bean MongoDbFactory mongoDbFactory(Mongo mongo) throws Exception {
		UserCredentials userCredentials = new UserCredentials(mongoUsername, mongoPassword);
		return new SimpleMongoDbFactory(mongo, mongoDatabase, userCredentials);
	}

	/*
	 * Factory bean that creates the com.mongodb.Mongo instance
	 */
	public @Bean MongoFactoryBean mongo() {
		MongoFactoryBean mongo = new MongoFactoryBean();
		mongo.setHost(mongoHost);
		mongo.setPort(mongoPort);
		return mongo;
	}
	
	public @Bean MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory){
		//remove _class
		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
			
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
		return mongoTemplate;
	}

}
