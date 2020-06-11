package digital.tutors.constructor.services

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.*
import digital.tutors.constructor.vo.progress.ProgressVO
import org.springframework.data.domain.Page
import java.awt.print.Pageable

interface ProgressService {

    @Throws(EntityNotFoundException::class)
    fun getProgressByLessonId(idLesson: String): List<ProgressVO>

    @Throws(EntityNotFoundException::class)
    fun getProgressByTopicId(idTopic: String): List<ProgressVO>

    @Throws(EntityNotFoundException::class)
    fun getProgressById(idProgress: String): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun getProgress(pageable: Pageable): Page<ProgressVO>

    fun createProgress(lessonProgress: List<LessonProgress>, topicProgress: List<TopicProgress>, userId: String): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun updateProgress(): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun deleteProgress(): ProgressVO

    fun toProgressVO(progress: Progress): ProgressVO
}
