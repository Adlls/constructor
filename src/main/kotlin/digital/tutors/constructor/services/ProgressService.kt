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
    fun getProgressById(idProgress: String): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun getProgress(pageable: Pageable): Page<ProgressVO>

    fun createLessonProgress(lessonProgress: List<LessonProgress>, userId: String): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun getProgressByCourseId(courseId: String): List<ProgressVO>

    fun getProgressByStudentId(idStudent: String): List<ProgressVO>

    @Throws(EntityNotFoundException::class)
    fun updateProgress(idLesson: String, userId: String, answerTest: Int): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun deleteProgress(idProgress: String): ProgressVO

    fun toProgressVO(progress: Progress): ProgressVO
}
