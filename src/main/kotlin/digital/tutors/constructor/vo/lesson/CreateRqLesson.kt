package digital.tutors.constructor.vo.lesson

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.core.entity.EntityRefRq
import digital.tutors.constructor.entities.Level
import digital.tutors.constructor.entities.RelationToLesson
import digital.tutors.constructor.entities.Topic
import digital.tutors.constructor.vo.level.LevelVO
import digital.tutors.constructor.vo.topic.TopicVO

data class CreateRqLesson(
        val author: EntityRefRq?,
        val topic: EntityRefRq?,
        val relations: RelationToLesson?,
        val levels: List<EntityRefRq>
)
