package digital.tutors.constructor.vo.topic

import digital.tutors.constructor.core.entity.EntityRefRq
import digital.tutors.constructor.entities.Fgos
import digital.tutors.constructor.entities.ProfStandard
import digital.tutors.constructor.entities.RelationToTopic
import digital.tutors.constructor.vo.course.CourseVO

data class CreateRqTopic(
        val title: String,
        val tags: List<String>?,
        val author: EntityRefRq?,
        val course: CourseVO?,
        val fgos: List<Fgos>?,
        val profStandard: List<ProfStandard>?,
        val relation: RelationToTopic?
)
