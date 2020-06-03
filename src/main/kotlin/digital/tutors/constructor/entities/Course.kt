package digital.tutors.constructor.entities

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.core.entity.AuditableEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document
data class Course (
        @Id
        var id: String? = null,

        var title: String? = null,
        var description: String? = null,

        @DBRef
        var author: User? = null,
        @DBRef
        var contributors: List<User>? = null,
        @DBRef
        var followers: List<User>? = null,

        var keywords: List<String>? = null,
        var skills: List<String>? = null,

        var typeAccess: TypeAccess? = TypeAccess.PUBLIC

        ): AuditableEntity()

enum class TypeAccess {
    PUBLIC, PRIVATE
}

enum class TypeRelation {
        EXTENDED, REPLACEABLE
}
