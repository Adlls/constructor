package digital.tutors.constructor.repositories

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.entities.Lesson
import digital.tutors.constructor.entities.LessonProgress
import digital.tutors.constructor.entities.Progress
import digital.tutors.constructor.entities.Topic
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ProgressRepository: MongoRepository<Progress, String> {
    //fun findAllByLessonProgressLesson(lesson: Lesson): List<Progress>
    //fun existsByLessonProgressProgress(lesson: Lesson): Boolean
    //fun existsByTopicProgressTopic(topic: Topic): Boolean
    fun existsProgressByStudentId(userId: String): Boolean
    fun findByStudentId(userId: String): Optional<Progress>
}
