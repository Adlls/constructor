package digital.tutors.constructor.repositories

import digital.tutors.constructor.entities.Lesson
import org.springframework.data.mongodb.repository.MongoRepository

interface LessonRepository : MongoRepository<Lesson, String> {
    fun findLessonById(lessonId: String): Lesson
}
