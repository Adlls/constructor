package digital.tutors.constructor.vo.lesson

import digital.tutors.constructor.core.entity.EntityRefRq
import digital.tutors.constructor.entities.RelationToLesson

data class CreateRqLesson(
        val author: EntityRefRq?,
        val topic: EntityRefRq?,
        val relations: RelationToLesson?,
        val levels: List<EntityRefRq>?
)
