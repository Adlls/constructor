package digital.tutors.constructor.vo.lesson

import digital.tutors.constructor.auth.vo.UserVO
import digital.tutors.constructor.entities.*
import digital.tutors.constructor.vo.CreatorVO
import digital.tutors.constructor.vo.VO
import digital.tutors.constructor.vo.level.LevelVO
import digital.tutors.constructor.vo.topic.CreatorTopicVO
import digital.tutors.constructor.vo.topic.TopicVO

data class LessonVO(
        val id: String?,
        val author: UserVO?,
        val topic: TopicVO?,
        val relation: RelationToLesson?,
        val levels: List<Level>?
): VO


class CreatorLessonVO {
    companion object: CreatorVO() {
        override fun fromData(entity: Entity): VO {
            val lesson = entity as Lesson
            return LessonVO(
                    lesson.id,
                    lesson.author?.let { UserVO.fromData(it, null) },
                    lesson.topic?.let { CreatorTopicVO.fromData(it) } as TopicVO,
                    lesson.relation?.apply {
                        this.lesson = lesson
                        lesson2 = null
                        typeRelation = null
                    },
                    lesson.levels
            )
        }
    }
}
