package digital.tutors.constructor.vo.progress

import digital.tutors.constructor.auth.vo.UserVO
import digital.tutors.constructor.entities.Entity
import digital.tutors.constructor.entities.LessonProgress
import digital.tutors.constructor.entities.Progress
import digital.tutors.constructor.vo.CreatorVO
import digital.tutors.constructor.vo.VO

data class ProgressVO(
        val id: String?,
        val lessonProgress: List<LessonProgress>?,
        val student: UserVO?
): VO

class CreatorProgressVO {
    companion object: CreatorVO() {
        override fun fromData(entity: Entity): VO {
            val progress = entity as Progress
            return ProgressVO (
                    progress.id,
                    progress.lessonProgress,
                    progress.student?.let { UserVO.fromData(it, null) }
            )
        }

    }
}
