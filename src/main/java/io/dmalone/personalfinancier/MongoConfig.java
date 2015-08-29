package io.dmalone.personalfinancier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;

@Configuration
public class MongoConfig {
//TODO - inject db name, username, password from environment 
	public @Bean MongoDbFactory mongoDbFactory(Mongo mongo) throws Exception {
		UserCredentials userCredentials = new UserCredentials("joe", "secret");
		return new SimpleMongoDbFactory(mongo, "database", userCredentials);
	}

	/*
	 * Factory bean that creates the com.mongodb.Mongo instance
	 */
	public @Bean MongoFactoryBean mongo() {
		MongoFactoryBean mongo = new MongoFactoryBean();
		mongo.setHost("localhost");
		mongo.setPort(27017);
		return mongo;
	}

}
