package digital.tutors.constructor.services

import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.vo.course.CourseVO
import digital.tutors.constructor.vo.course.CreateRqCourse
import digital.tutors.constructor.vo.course.UpdateRqCourse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CourseService {

    @Throws(EntityNotFoundException::class)
    fun createCourse(createRqCourse: CreateRqCourse): CourseVO

    @Throws(EntityNotFoundException::class)
    fun updateCourse(id: String, updateRqCourse: UpdateRqCourse): CourseVO

    @Throws(EntityNotFoundException::class)
    fun getAllCourses(pageable: Pageable): Page<CourseVO>

    @Throws(EntityNotFoundException::class)
    fun getCourseById(id: String): CourseVO

    @Throws(EntityNotFoundException::class)
    fun delete(id: String): CourseVO


}
