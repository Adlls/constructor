package digital.tutors.constructor.repositories

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.entities.Course
import digital.tutors.constructor.entities.TypeAccess
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CourseRepository: MongoRepository<Course, String> {
    fun findByTypeAccessEqualsOrFollowersContains(typeAccess: TypeAccess, user: User, pageable: Pageable): Page<Course>
    fun findByContributorsContainsOrAuthor(user: User, pageable: Pageable): Page<Course>

    fun findByTypeAccessEqualsOrFollowersContains(typeAccess: TypeAccess, user: User): Course
    fun findByContributorsContainsOrAuthor(user: User): Course

    fun existsCourseByAuthor(user: User): Boolean
    fun existsCourseByContributors(user: User): Boolean
}
