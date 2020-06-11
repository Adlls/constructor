package digital.tutors.constructor.repositories

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.entities.Course
import digital.tutors.constructor.entities.TypeAccess
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CourseRepository: MongoRepository<Course, String> {

}
