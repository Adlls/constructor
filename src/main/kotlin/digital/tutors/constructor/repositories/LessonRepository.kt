package digital.tutors.constructor.repositories

import digital.tutors.constructor.entities.Lesson
import digital.tutors.constructor.entities.Topic
import org.springframework.data.mongodb.repository.MongoRepository

interface LessonRepository : MongoRepository<Lesson, String> {
    fun findLessonById(lessonId: String): Lesson
    fun findLessonsByTopicId(topicId: String): List<Lesson>
}
