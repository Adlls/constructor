package digital.tutors.constructor.services

import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.Lesson
import digital.tutors.constructor.vo.VO
import digital.tutors.constructor.vo.lesson.CreateRqLesson
import digital.tutors.constructor.vo.lesson.LessonVO
import digital.tutors.constructor.vo.lesson.UpdateRqLesson
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface LessonService {

    @Throws(EntityNotFoundException::class)
    fun getAllLessons(pageable: Pageable): Page<LessonVO>

    @Throws(EntityNotFoundException::class)
    fun getLessonById(id: String): LessonVO

    @Throws(EntityNotFoundException::class)
    fun createLesson(createRqLesson: CreateRqLesson): LessonVO

    @Throws(EntityNotFoundException::class)
    fun updateLesson(id: String, updateRqLesson: UpdateRqLesson): LessonVO

    @Throws(EntityNotFoundException::class)
    fun deleteLesson(id: String): LessonVO

    fun toVO(lesson: Lesson): LessonVO

}
