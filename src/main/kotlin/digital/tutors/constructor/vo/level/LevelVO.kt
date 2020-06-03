package digital.tutors.constructor.vo.level

import digital.tutors.constructor.entities.Lesson
import digital.tutors.constructor.entities.Level
import digital.tutors.constructor.vo.lesson.LessonVO

data class LevelVO(
        val id: String?,
        val htmlBody: String?,
        val enabled: Boolean?,
        val lesson: Lesson?
)
{
 companion object {
     fun fromData(level: Level): LevelVO =
             LevelVO (
                     level.id,
                     level.htmlBody,
                     level.enabled,
                     level.lesson
             )
    }
}
