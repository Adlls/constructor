package digital.tutors.constructor.vo.level

import digital.tutors.constructor.entities.Entity
import digital.tutors.constructor.entities.Level
import digital.tutors.constructor.vo.CreatorVO
import digital.tutors.constructor.vo.VO
import digital.tutors.constructor.vo.lesson.CreatorLessonVO
import digital.tutors.constructor.vo.lesson.LessonVO

data class LevelVO(
        val id: String?,
        val htmlBody: String?,
        val enabled: Boolean?,
        val lessonId: String?
): VO

class CreatorLevelVO {
    companion object: CreatorVO() {
        override fun fromData(entity: Entity): VO {
           val level = entity as Level
           return LevelVO (
                    level.id,
                    level.htmlBody,
                    level.enabled,
                    level.lessonId
           )
        }
    }
}
