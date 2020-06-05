package digital.tutors.constructor.vo.topic

import digital.tutors.constructor.auth.vo.UserVO
import digital.tutors.constructor.entities.*
import digital.tutors.constructor.vo.CreatorVO
import digital.tutors.constructor.vo.VO
import digital.tutors.constructor.vo.course.CourseVO
import digital.tutors.constructor.vo.course.CreatorCourseVO

data class TopicVO(
        val id: String?,
        val title: String?,
        val tags: List<String>?,
        val author: UserVO?,
        val course: CourseVO?,
        val fgos: List<Fgos>?,
        val profStandard: List<ProfStandard>?,
        val relation: RelationToTopic?
): VO

class CreatorTopicVO {
    companion object: CreatorVO() {
        override fun fromData(entity: Entity): VO {
            val topic = entity as Topic
            return TopicVO(
                    topic.id,
                    topic.title,
                    topic.tags,
                    topic.author?.let { UserVO.fromData(it, null) },
                    topic.course?.let { CreatorCourseVO.fromData(it) } as CourseVO?,
                    topic.fgos,
                    topic.profStandard,
                    topic.relation?.apply {
                        this.topic = topic
                        ref = null
                        typeRelation = null
                    }
            )
        }

    }
}
