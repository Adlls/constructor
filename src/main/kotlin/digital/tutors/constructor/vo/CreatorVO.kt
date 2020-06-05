package digital.tutors.constructor.vo

import digital.tutors.constructor.entities.Entity
import digital.tutors.constructor.entities.Topic


abstract class CreatorVO {
    abstract fun fromData(entity: Entity): VO
}

interface VO { }
