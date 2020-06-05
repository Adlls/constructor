package digital.tutors.constructor.vo.fgos
import digital.tutors.constructor.entities.Entity
import digital.tutors.constructor.entities.Fgos
import digital.tutors.constructor.vo.CreatorVO
import digital.tutors.constructor.vo.VO

data class FgosVO(
        val id: String?,
        val numFgos: String?,
        val title: String?,
        val description: String?

): VO

class creatorFgosVO {
    companion object: CreatorVO() {
        override fun fromData(entity: Entity): VO {
            val fgos = entity as Fgos
            return FgosVO(
                    fgos.id,
                    fgos.numFgos,
                    fgos.title,
                    fgos.description
            )
        }

    }
}
