package digital.tutors.constructor.services

import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.Course
import digital.tutors.constructor.entities.Topic
import digital.tutors.constructor.vo.course.CourseVO
import digital.tutors.constructor.vo.course.CreateRqCourse
import digital.tutors.constructor.vo.course.UpdateRqCourse
import digital.tutors.constructor.vo.topic.TopicVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CourseService {


    @Throws(EntityNotFoundException::class)
    fun createCourseByUserId(createRqCourse: CreateRqCourse, userId: String): CourseVO

    @Throws(EntityNotFoundException::class)
    fun updateCourseByUserId(id: String, updateRqCourse: UpdateRqCourse, userId: String): CourseVO

    @Throws(EntityNotFoundException::class)
    fun getAllCoursesByUserId(pageable: Pageable, idUser: String): Page<CourseVO>

    @Throws(EntityNotFoundException::class)
    fun getCourseByIdAndByUserId(id: String, userId: String): CourseVO

    @Throws(EntityNotFoundException::class)
    fun deleteCourseByUserId(id: String, userId: String): CourseVO

    @Throws(EntityNotFoundException::class)
    fun getCoursesByAuthorId(userId: String): List<Course>

    @Throws(EntityNotFoundException::class)
    fun getCoursesByFollowers(userId: String): List<Course>

    @Throws(EntityNotFoundException::class)
    fun getCoursesByDistributors(userId: String): List<Course>

    fun toVO(course: Course): CourseVO


}
