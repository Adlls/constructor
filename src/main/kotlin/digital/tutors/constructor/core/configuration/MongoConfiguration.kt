package digital.tutors.constructor.core.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["digital.tutors.*"])
class MongoConfiguration {}
