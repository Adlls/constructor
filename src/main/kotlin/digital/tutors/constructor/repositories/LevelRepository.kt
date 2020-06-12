package digital.tutors.constructor.repositories

import digital.tutors.constructor.entities.Levels
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface LevelRepository: MongoRepository<Levels, String> {
    fun findLevelByLessonId(lessonId: String): Levels
    fun findByLessonId(lessonId: String): Optional<Levels>
    fun existsByLessonId(idLesson: String): Boolean
}
