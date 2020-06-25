package digital.tutors.constructor.repositories

import digital.tutors.constructor.entities.Topic
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TopicRepository : MongoRepository<Topic, String> {
    fun findTopicById(idTopic: String): Topic
    fun findTopicByCourseId(idCourse: String): Optional<Topic>
}
