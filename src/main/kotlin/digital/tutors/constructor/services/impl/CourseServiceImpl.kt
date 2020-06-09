package digital.tutors.constructor.services.impl

import digital.tutors.constructor.auth.entities.Roles
import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.repositories.UserRepository
import digital.tutors.constructor.auth.services.impl.UserServiceImpl
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.Course
import digital.tutors.constructor.entities.TypeAccess
import digital.tutors.constructor.repositories.CourseRepository
import digital.tutors.constructor.services.CourseService
import digital.tutors.constructor.vo.course.CourseVO
import digital.tutors.constructor.vo.course.CreateRqCourse
import digital.tutors.constructor.vo.course.CreatorCourseVO
import digital.tutors.constructor.vo.course.UpdateRqCourse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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


    fun getUser(userId: String): User {
        return  userRepository.findByIdOrNull(userId)
                ?: throw EntityNotFoundException("User with id $userId not found")
    }

    override fun createCourseByUserId(createRqCourse: CreateRqCourse, userId: String): CourseVO {
        val user = getUser(userId)
        //Студент не может создавать курсы
        if (user.role == Roles.ROLE_STUDENT) {
            throw ExceptionInInitializerError("Denied")
        } else {
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
            return getCourseByIdAndByUserId(id, userId)
        }
    }

    override fun updateCourseByUserId(id: String, updateRqCourse: UpdateRqCourse, userId: String): CourseVO {
        val user = getUser(userId)
        //Проверяем не студент ли
        //Также проверяем, если это преподователь, то нужно чтобы он был либо автором либо соавтором
        if (user.role != Roles.ROLE_STUDENT) {
            if ((user.role == Roles.ROLE_TEACHER
                            && courseRepository.existsCourseByAuthor(user))
                    || courseRepository.existsCourseByContributors(user)) {
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
                return getCourseByIdAndByUserId(id, userId)
            } else {
                throw ExceptionInInitializerError("Denied")
            }
        } else {
            throw ExceptionInInitializerError("Denied")
        }
    }

    @Throws(EntityNotFoundException::class)
    override fun getAllCoursesByUserId(pageable: Pageable, userId: String): Page<CourseVO> {
        val user = getUser(userId)

        return when (user.role) {
            Roles.ROLE_STUDENT -> {
                //Если студент пытается попасть на публичный курс, у него это легко выйдет
                //Если он попытается попасть на приватный курс и если его нет в массиве followers,
                // то он не получит доступ, хех хацкер
                courseRepository.findByTypeAccessEqualsOrFollowersContains(TypeAccess.PUBLIC, user, pageable).map(::toVO)
            }
            Roles.ROLE_TEACHER -> {
                //Получить курсы могут только те преподы, которые являются сооавторами или создателями курса
                courseRepository.findByContributorsContainsOrAuthor(user, pageable).map(::toVO)
            }
            else -> {
                throw EntityNotFoundException("Not found course by $userId")
            }
        }
    }

    @Throws(EntityNotFoundException::class)
    override fun getCourseByIdAndByUserId(id: String, userId: String): CourseVO {
        val user = getUser(userId)

        //Логика такая же, как и для getAllCoursesByUserId
        return when (user.role) {
            Roles.ROLE_STUDENT -> {
                toVO(courseRepository.findByTypeAccessEqualsOrFollowersContains(TypeAccess.PUBLIC, user))
            }
            Roles.ROLE_TEACHER -> {
                toVO(courseRepository.findByContributorsContainsOrAuthor(user))
            }
            else -> {
                throw EntityNotFoundException("Course with $id not found")
            }
        }
    }



    override fun deleteCourseByUserId(id: String, userId: String): CourseVO {
        val course = getCourseByIdAndByUserId(id, userId)
        val user = getUser(userId)

        //Проверяем, является ли пользователь студентом, если да, то не даем ему удалять курс
        //Если это преподователь и если он автор этого курса, то он может удалить этот курс
        if (user.role != Roles.ROLE_STUDENT) {
            if (user.role == Roles.ROLE_TEACHER && courseRepository.existsCourseByAuthor(user)) {
                courseRepository.deleteById(id)
                log.debug("Deleted course with $id")
                return course
            } else {
                throw ExceptionInInitializerError("Denied")
            }
        } else {
            throw ExceptionInInitializerError("Denied")
        }
    }

    override fun toVO(course: Course): CourseVO {
        return CreatorCourseVO.fromData(course) as CourseVO
    }

}
