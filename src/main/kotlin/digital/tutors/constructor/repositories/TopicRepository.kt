package digital.tutors.constructor.repositories

import digital.tutors.constructor.entities.Topic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface TopicRepository : MongoRepository<Topic, String> {
    fun findTopicByCourseId(courseId: String): Topic
    fun findAllByCourseId(courseId: String, pageable: Pageable): Page<Topic>
}
