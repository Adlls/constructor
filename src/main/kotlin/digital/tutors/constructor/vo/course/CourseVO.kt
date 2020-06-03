package digital.tutors.constructor.vo.course

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.vo.UserVO
import digital.tutors.constructor.entities.Course
import digital.tutors.constructor.entities.TypeAccess

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
        ) {
            companion object {
                fun fromData(course: Course): CourseVO =
                        CourseVO (
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
