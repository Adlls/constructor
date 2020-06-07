package digital.tutors.constructor.entities

import org.springframework.data.annotation.Id

data class Level(
        @Id
        var id: String? = null,
        var htmlBody: String? = null,
        var enabled: Boolean? = false,
        var lessonId: String? = null
): Entity
