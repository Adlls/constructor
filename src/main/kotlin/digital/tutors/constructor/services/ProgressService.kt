package digital.tutors.constructor.services

import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.vo.progress.ProgressVO

interface ProgressService {
    @Throws(EntityNotFoundException::class)
    fun getProgressByLessonId(idLesson: String): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun getProgressByTopicId(idTopic: String): ProgressVO

    fun createProgress(): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun updateProgress(): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun deleteProgress(): ProgressVO
}
