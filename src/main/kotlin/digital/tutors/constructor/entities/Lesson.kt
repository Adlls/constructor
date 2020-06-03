package digital.tutors.constructor.entities

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.core.entity.AuditableEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Lesson(
        @Id
        var id: String? = null,

        @DBRef
        var author: User? = null,
        @DBRef
        var topic: Topic? = null,
        var relation: RelationToLesson? = null,
        var levels: List<Level>? = null


): AuditableEntity(), RelationEntities

data class RelationToLesson(

        @DBRef
        var lesson: Lesson? = null,
        @DBRef
        var lesson2: Lesson? = null,
        var typeRelation: TypeRelation? = null
)
