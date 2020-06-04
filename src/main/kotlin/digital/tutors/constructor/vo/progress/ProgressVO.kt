package digital.tutors.constructor.vo.progress

import digital.tutors.constructor.auth.vo.UserVO
import digital.tutors.constructor.entities.LessonProgress
import digital.tutors.constructor.entities.Progress
import digital.tutors.constructor.entities.TopicProgress

data class ProgressVO(
        val id: String?,
        val lessonProgress: LessonProgress?,
        val topicProgress: TopicProgress?,
        val student: UserVO?
)
{
    companion object {
        fun fromData(progress: Progress) : ProgressVO =
                ProgressVO (
                        progress.id,
                        progress.lessonProgress,
                        progress.topicProgress,
                        progress.student?.let { UserVO.fromData(it, null) }
                )


    }
}
