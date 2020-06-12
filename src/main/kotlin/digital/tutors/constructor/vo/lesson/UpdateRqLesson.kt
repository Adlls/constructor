package digital.tutors.constructor.vo.lesson

import digital.tutors.constructor.core.entity.EntityRefRq
import digital.tutors.constructor.entities.RelationToLesson

data class UpdateRqLesson(
        val topic: EntityRefRq?,
        val relations: RelationToLesson?,
        val levels: EntityRefRq?
)
