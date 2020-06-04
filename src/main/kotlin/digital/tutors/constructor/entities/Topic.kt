package digital.tutors.constructor.entities

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.core.entity.AuditableEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

data class Topic(
        @Id
        var id: String? = null,

        var title: String? = null,
        var tags: List<String>? = null,

        @DBRef
        var author: User? = null,
        @DBRef
        var course: Course? = null,
        @DBRef
        var fgos: List<Fgos>? = null,
        @DBRef
        var profStandard: List<ProfStandard>? = null,
        var relation: RelationToTopic? = null

        ): AuditableEntity(), RelationEntities

data class RelationToTopic (
        var topic: Topic? = null,
        var ref: RelationEntities? = null,
        var typeRelation: TypeRelation? = null
)
