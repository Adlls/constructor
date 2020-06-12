package digital.tutors.constructor.services

interface TestService {

    fun getResultByLessonId(answer: Int, lessonId: String): Boolean
}
