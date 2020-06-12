package digital.tutors.constructor.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

data class Levels(
        @Id
        var id: String? = null,
        var bodyLevels: List<BodyLevel>? = null,
        var lesson: Lesson? = null
): Entity

data class BodyLevel(
        var enabled: Boolean? = true,
        var htmlBody: String? = null,
        var numLevel: Int? = null
)
