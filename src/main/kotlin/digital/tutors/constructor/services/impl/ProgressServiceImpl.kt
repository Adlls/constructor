package digital.tutors.constructor.services.impl

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.repositories.UserRepository
import digital.tutors.constructor.auth.services.impl.UserServiceImpl
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.*
import digital.tutors.constructor.repositories.LessonRepository
import digital.tutors.constructor.repositories.ProgressRepository
import digital.tutors.constructor.repositories.TopicRepository
import digital.tutors.constructor.services.ProgressService
import digital.tutors.constructor.vo.progress.CreatorProgressVO
import digital.tutors.constructor.vo.progress.ProgressVO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.awt.print.Pageable
import java.lang.IllegalArgumentException

@Service
class ProgressServiceImpl: ProgressService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Autowired
    lateinit var progressRepository: ProgressRepository

    @Autowired
    lateinit var lessonRepository: LessonRepository

    @Autowired
    lateinit var topicRepository: TopicRepository

    @Autowired
    lateinit var userRepository: UserRepository

    override fun getProgressByLessonId(idLesson: String): List<ProgressVO> {
        val lesson = lessonRepository.findLessonById(idLesson)
        return progressRepository.findAllByLessonProgressLesson(lesson).map(::toProgressVO)
    }

    override fun getProgressByTopicId(idTopic: String): List<ProgressVO> {
        val topic = topicRepository.findTopicById(idTopic)
        return progressRepository.findAllByTopicProgressProgress(topic).map(::toProgressVO)
    }

    override fun getProgressById(idProgress: String): ProgressVO {
        return progressRepository.findById(idProgress).map(::toProgressVO).orElseThrow {
            throw EntityNotFoundException("Not found progress with id $idProgress")
        }
    }

    override fun getProgress(pageable: Pageable): Page<ProgressVO> {
        TODO("Not yet implemented")
    }

    override fun createProgress(lessonProgress: List<LessonProgress>, topicProgress: List<TopicProgress>, userId: String): ProgressVO {
      val user = userRepository.findUserById(userId)
      val id = progressRepository.save(Progress().apply {
            this.lessonProgress = lessonProgress
            this.topicProgress = topicProgress
            student = user
        }).id?: throw IllegalArgumentException("Bad request")

        return getProgressById(id)
    }

    override fun updateProgress(): ProgressVO {
        TODO("Not yet implemented")
    }

    override fun deleteProgress(): ProgressVO {
        TODO("Not yet implemented")
    }

    override fun toProgressVO(progress: Progress): ProgressVO {
        return CreatorProgressVO.fromData(progress) as ProgressVO
    }
}
