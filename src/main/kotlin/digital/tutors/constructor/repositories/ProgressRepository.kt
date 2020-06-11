package digital.tutors.constructor.repositories

import digital.tutors.constructor.entities.Lesson
import digital.tutors.constructor.entities.Progress
import digital.tutors.constructor.entities.Topic
import org.springframework.data.mongodb.repository.MongoRepository

interface ProgressRepository: MongoRepository<Progress, String> {
    fun findAllByLessonProgressLesson(lesson: Lesson): List<Progress>
    fun findAllByTopicProgressProgress(topic: Topic): List<Progress>

    fun existsByLessonProgressProgress(lesson: Lesson): Boolean
    fun existsByTopicProgressTopic(topic: Topic): Boolean
}
