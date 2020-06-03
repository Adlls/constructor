package digital.tutors.constructor.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef

data class Level(
        @Id
        var id: String? = null,
        var htmlBody: String? = null,
        var enabled: Boolean? = true,
        @DBRef
        var lesson: Lesson? = null

)
