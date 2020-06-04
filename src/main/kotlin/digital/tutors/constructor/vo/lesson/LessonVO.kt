package digital.tutors.constructor.vo.lesson

import digital.tutors.constructor.auth.vo.UserVO
import digital.tutors.constructor.entities.Lesson
import digital.tutors.constructor.entities.Level
import digital.tutors.constructor.entities.RelationToLesson
import digital.tutors.constructor.entities.Topic
import digital.tutors.constructor.vo.level.LevelVO
import digital.tutors.constructor.vo.topic.TopicVO

data class LessonVO(
        val id: String?,
        val author: UserVO?,
        val topic: TopicVO?,
        val relation: RelationToLesson?,
        val levels: List<Level>?
) {
    companion object {
        fun fromData(lesson: Lesson): LessonVO =
                LessonVO(
                        lesson.id,
                        lesson.author?.let { UserVO.fromData(it, null) },
                        lesson.topic?.let { TopicVO.fromData(it) },
                        lesson.relation?.apply {
                            this.lesson = lesson
                            lesson2 = null
                            typeRelation = null
                        },
                        lesson.levels
                )
    }
}
