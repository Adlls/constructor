package digital.tutors.constructor.vo.topic

import digital.tutors.constructor.auth.vo.UserVO
import digital.tutors.constructor.entities.*
import digital.tutors.constructor.vo.course.CourseVO

data class TopicVO(
        val id: String?,
        val title: String?,
        val tags: List<String>?,
        val author: UserVO?,
        val course: Course?,
        val fgos: List<Fgos>?,
        val profStandard: List<ProfStandard>?,
        val relation: RelationToTopic?
        )
{
    companion object {
        fun fromData(topic: Topic): TopicVO =
                TopicVO(
                    topic.id,
                    topic.title,
                    topic.tags,
                    topic.author?.let { UserVO.fromData(it, null) },
                    topic.course,
                    topic.fgos,
                    topic.profStandard,
                    topic.relation
                )
    }
}
