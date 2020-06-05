package digital.tutors.constructor.vo.profStandard

import digital.tutors.constructor.entities.Entity
import digital.tutors.constructor.entities.ProfStandard
import digital.tutors.constructor.vo.CreatorVO
import digital.tutors.constructor.vo.VO

data class ProfStandardVO(
        val id: String?,
        var numProfStandard: String?,
        var title: String?,
        var description: String?
): VO

class CreatorProfStandardVO {
    companion object: CreatorVO() {
        override fun fromData(entity: Entity): VO {
            val profStandard = entity as ProfStandard
            return ProfStandardVO(
                    profStandard.id,
                    profStandard.numProfStandard,
                    profStandard.title,
                    profStandard.description
            )
        }

    }
}
