package digital.tutors.constructor.vo.course

import digital.tutors.constructor.core.entity.EntityRefRq
import digital.tutors.constructor.entities.TypeAccess

data class UpdateRqCourse(
        val title: String?,
        val description: String?,
        val contributors: List<EntityRefRq>?,
        val followers: List<EntityRefRq>?,
        val keywords: List<String>?,
        val skills: List<String>?,
        val typeAccess: TypeAccess?
)
