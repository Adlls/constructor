package digital.tutors.constructor.vo.topic

import digital.tutors.constructor.entities.Fgos
import digital.tutors.constructor.entities.ProfStandard
import digital.tutors.constructor.entities.RelationToTopic

data class UpdateRqTopic(
        val title: String,
        val tags: List<String>?,
        val fgos: List<Fgos>?,
        val profStandard: List<ProfStandard>?,
        val relation: RelationToTopic?
)
