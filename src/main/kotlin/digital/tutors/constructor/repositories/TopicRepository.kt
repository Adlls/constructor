package digital.tutors.constructor.repositories

import digital.tutors.constructor.entities.Topic
import org.springframework.data.mongodb.repository.MongoRepository

interface TopicRepository : MongoRepository<Topic, String> {
    fun findTopicById(idTopic: String): Topic

}
