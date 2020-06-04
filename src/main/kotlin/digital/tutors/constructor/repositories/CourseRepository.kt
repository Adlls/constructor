package digital.tutors.constructor.repositories

import digital.tutors.constructor.entities.Course
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CourseRepository: MongoRepository<Course, String> {

}
