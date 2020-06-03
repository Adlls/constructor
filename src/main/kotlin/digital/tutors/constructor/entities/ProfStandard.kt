package digital.tutors.constructor.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class ProfStandard (
        @Id
        var id: String? = null,

        var numProfStandard: Int? = null,
        var title: String? = null,
        var description: String? = null
)
