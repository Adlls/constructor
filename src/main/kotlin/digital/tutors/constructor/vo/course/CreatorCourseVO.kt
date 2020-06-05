package digital.tutors.constructor.vo.course

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.vo.UserVO
import digital.tutors.constructor.entities.Course
import digital.tutors.constructor.entities.Entity
import digital.tutors.constructor.entities.Topic
import digital.tutors.constructor.entities.TypeAccess
import digital.tutors.constructor.vo.CreatorVO
import digital.tutors.constructor.vo.VO
import digital.tutors.constructor.vo.topic.TopicVO

data class CourseVO (
        val id: String?,
        val title: String?,
        val description: String?,
        val author: UserVO?,
        val contributors: List<UserVO>?,
        val followers: List<UserVO>?,
        val keywords: List<String>?,
        val skills: List<String>?,
        val typeAccess: TypeAccess?
        ): VO

class CreatorCourseVO {
    companion object: CreatorVO() {
        override fun fromData(entity: Entity): VO {
            val course = entity as Course
            return CourseVO(
                    course.id,
                    course.title,
                    course.description,
                    course.author.let { UserVO.fromData(it, null) },
                    course.contributors?.map { UserVO.fromData(it, null)},
                    course.followers?.map { UserVO.fromData(it, null)},
                    course.keywords,
                    course.skills,
                    course.typeAccess
            )
        }

    }
}
