package digital.tutors.constructor.services.impl

import com.mongodb.MongoClient
import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.repositories.UserRepository
import digital.tutors.constructor.auth.services.impl.UserServiceImpl
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.*
import digital.tutors.constructor.repositories.CourseRepository
import digital.tutors.constructor.services.CourseService
import digital.tutors.constructor.services.ProgressService
import digital.tutors.constructor.vo.course.CourseVO
import digital.tutors.constructor.vo.course.CreateRqCourse
import digital.tutors.constructor.vo.course.CreatorCourseVO
import digital.tutors.constructor.vo.course.UpdateRqCourse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class CourseServiceImpl: CourseService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var progressService: ProgressService

    fun getUser(userId: String): User {
        return  userRepository.findByIdOrNull(userId)
                ?: throw EntityNotFoundException("User with id $userId not found")
    }

    override fun createCourse(createRqCourse: CreateRqCourse): CourseVO {
            val id = courseRepository.save(Course().apply {
                title = createRqCourse.title
                description = createRqCourse.description
                author = User(createRqCourse.author?.id)
                contributors = createRqCourse.contributors?.map { User(it.id) }
                followers = createRqCourse.followers?.map { User(it.id) }
                keywords = createRqCourse.keywords
                skills = createRqCourse.skills
                typeAccess = createRqCourse.typeAccess
            }).id ?: throw IllegalArgumentException("Bad id returned")
            log.debug("Created course $id")

            return getCourseById(id)
    }

    override fun updateCourse(id: String, updateRqCourse: UpdateRqCourse): CourseVO {
                courseRepository.save(courseRepository.findById(id).get().apply {
                    title = updateRqCourse.title
                    description = updateRqCourse.description
                    contributors = updateRqCourse.contributors?.map { User(it.id) }
                    followers = updateRqCourse.followers?.map { User(it.id) }
                    keywords = updateRqCourse.keywords
                    skills = updateRqCourse.skills
                    typeAccess = updateRqCourse.typeAccess
                })
                log.debug("Updated course $id")
                return getCourseById(id)
    }


    @Throws(EntityNotFoundException::class)
    override fun getAllCourses(pageable: Pageable): Page<CourseVO> {
        return courseRepository.findAll(pageable).map(::toVO)
    }


    @Throws(EntityNotFoundException::class)
    override fun getCourseById(id: String): CourseVO {
        return courseRepository.findById(id).map(::toVO).orElseThrow {
            throw EntityNotFoundException("Course with $id not found")
        }
    }

    override fun deleteCourse(id: String): CourseVO {
        val course = getCourseById(id)
        courseRepository.deleteById(id)
        log.debug("Deleted course with $id")
        return course
    }

    override fun toVO(course: Course): CourseVO {
        return CreatorCourseVO.fromData(course) as CourseVO
    }

}
