package digital.tutors.constructor.entities

import digital.tutors.constructor.auth.entities.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Progress(
        @Id
        var id: String? = null,
        var lessonProgress: List<LessonProgress>? = null,
        var student: User? = null
): Entity

/*
        data class TopicProgress (
                var progress: Int? = null,
                var topic: Topic? = null
        )
*/

data class LessonProgress (
        var progress: Int? = null,
        var currentLevel: Int? = 1,
        var lesson: Lesson? = null
)
