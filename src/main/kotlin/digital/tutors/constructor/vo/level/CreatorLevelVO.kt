package digital.tutors.constructor.vo.level

import digital.tutors.constructor.entities.Entity
import digital.tutors.constructor.entities.Levels
import digital.tutors.constructor.entities.BodyLevel
import digital.tutors.constructor.vo.CreatorVO
import digital.tutors.constructor.vo.VO
import digital.tutors.constructor.vo.lesson.CreatorLessonVO
import digital.tutors.constructor.vo.lesson.LessonVO

data class LevelVO(
        val id: String?,
        val bodyLevels: List<BodyLevel>?,
        val lesson: LessonVO?
): VO

class CreatorLevelVO {
    companion object: CreatorVO() {
        override fun fromData(entity: Entity): VO {
           val level = entity as Levels
           return LevelVO (
                    level.id,
                    level.bodyLevels,
                    level.lesson?.let { CreatorLessonVO.fromData(it) } as LessonVO?
           )
        }
    }
}
