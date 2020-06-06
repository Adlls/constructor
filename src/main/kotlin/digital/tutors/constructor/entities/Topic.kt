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

        var fgos: List<Fgos>? = null,
        var profStandard: List<ProfStandard>? = null,

        var relations: RelationToTopic? = null

        ): AuditableEntity(), Entity, RelationEntities

data class Fgos (
        var numFgos: String? = null,
        var title: String? = null,
        var description: String? = null
)

data class ProfStandard (
        var numProfStandard: String? = null,
        var title: String? = null,
        var description: String? = null
)

data class RelationToTopic (
        var refTopics: List<BodyRefTopic>? = null,
        var refLessons: List<BodyRefLesson>? = null
)

data class BodyRefTopic (
        var idTopic: String?,
        var typeRelation: TypeRelation?
)

data class BodyRefLesson (
        var idLesson: String?,
        var typeRelation: TypeRelation?
)
