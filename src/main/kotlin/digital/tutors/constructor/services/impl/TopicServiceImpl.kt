package digital.tutors.constructor.services.impl

import digital.tutors.constructor.auth.entities.Roles
import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.repositories.UserRepository
import digital.tutors.constructor.auth.services.impl.UserServiceImpl
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.*
import digital.tutors.constructor.repositories.CourseRepository
import digital.tutors.constructor.repositories.TopicRepository
import digital.tutors.constructor.services.TopicService
import digital.tutors.constructor.vo.topic.CreateRqTopic
import digital.tutors.constructor.vo.topic.CreatorTopicVO
import digital.tutors.constructor.vo.topic.TopicVO
import digital.tutors.constructor.vo.topic.UpdateRqTopic
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class TopicServiceImpl: TopicService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Autowired
    lateinit var topicRepository: TopicRepository

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var userRepository: UserRepository

    fun getUser(userId: String): User {
        return  userRepository.findByIdOrNull(userId)
                ?: throw EntityNotFoundException("User with id $userId not found")
    }

    /*
        TODO: Проверим, подписан ли студент на курс текущей темы
    */
    fun isSubscribe(idCourse: String, user: User): Boolean {
       return courseRepository.existsCourseByIdAndFollowers(idCourse, user)
    }

    /*
        TODO: Проверим, является ли препод соавтором или же автором курса для текущей темы
    */
    fun isContributorsOrAuthor(idCourse: String, user: User): Boolean {
        return courseRepository.existsCourseByIdAndAuthor(idCourse, user)
                || courseRepository.existsCourseByIdAndContributors(idCourse, user)
    }

    /*
        TODO: Проверяем запрос для студента, если подписан на курс, то даем ему топик
        TODO: Првоеряем запрос для преподователя, если сооавтор или автор, то даем ему доступ к топику
    */
    fun isAllowAccess(idCourse: String, user: User): Boolean {
        return (user.role == Roles.ROLE_STUDENT && isSubscribe(idCourse, user))
                || (user.role == Roles.ROLE_TEACHER && isContributorsOrAuthor(idCourse, user))
    }

    @Throws(EntityNotFoundException::class)
    override fun getTopicById(id: String, userId: String): TopicVO {
        val user = getUser(userId)
        val topic = topicRepository.findById(id)
        val idCourse = topic.get().course?.id.toString()

        return if (isAllowAccess(idCourse, user)) {
            topicRepository.findById(id).map(::toVO).orElseThrow {
                throw EntityNotFoundException("Topic not found with $id")
            }
        }
        else throw EntityNotFoundException("Not found topic by user id $userId")

    }


    @Throws(EntityNotFoundException::class)
    override fun getAllTopics(pageable: Pageable, userId: String, courseId: String): Page<TopicVO> {
        val user = getUser(userId)
        /*
            TODO: Проверяем топик по текущему курсу.
                  Если пользователь имеет доступ к курсу,
                  то имеет право получить топик по текущему курсу
        */
        return if (isAllowAccess(courseId, user)) {
            topicRepository.findAllByCourseId(courseId, pageable).map(::toVO)
        } else
            throw EntityNotFoundException("for user with id $userId not found topic")
    }

    override fun createTopic(createRqTopic: CreateRqTopic, userId: String): TopicVO {
        val user = getUser(userId)

        when (user.role) {
            Roles.ROLE_STUDENT -> {
                throw ExceptionInInitializerError("Denied")
            }
            Roles.ROLE_TEACHER -> {
                val id = topicRepository.save(Topic().apply {
                    title = createRqTopic.title
                    tags = createRqTopic.tags
                    author = User(id = createRqTopic.author?.id)
                    course = Course(id = createRqTopic.course?.id)
                    fgos = createRqTopic.fgos
                    profStandard = createRqTopic.profStandard
                    relations = createRqTopic.relations

                }).id ?: throw IllegalArgumentException("Bad id request")
                log.debug("Created topic $id")
                return getTopicById(id, userId)
            }
            else -> {
                throw ExceptionInInitializerError("Denied")
            }
        }
    }
    @Throws(EntityNotFoundException::class)
    override fun updateTopic(id: String, updateRqTopic: UpdateRqTopic, userId: String): TopicVO {
        val  user = getUser(userId)
        val topic = topicRepository.findById(id)
        val idCourse = topic.get().course?.id.toString()
        when {
            user.role == Roles.ROLE_STUDENT -> throw ExceptionInInitializerError("Denied")

            isContributorsOrAuthor(idCourse, user) -> {
                topicRepository.save(topicRepository.findById(id).get().apply {
                    title = updateRqTopic.title
                    tags = updateRqTopic.tags
                    fgos = updateRqTopic.fgos
                    profStandard = updateRqTopic.profStandard
                    relations = updateRqTopic.relations

                })
                log.debug("Updated topic $id")
                return getTopicById(id, userId)
            }
            else -> {
                throw ExceptionInInitializerError("Denied")
            }
        }
    }

    @Throws(EntityNotFoundException::class)
    override fun deleteTopic(id: String, userId: String): TopicVO {
        val user = getUser(userId)
        val topic = topicRepository.findById(id)
        val idCourse = topic.get().course?.id.toString()
        when {
            user.role == Roles.ROLE_STUDENT -> throw ExceptionInInitializerError("Denied")

            courseRepository.existsCourseByIdAndAuthor(idCourse, user) -> {
                val topic = getTopicById(id, userId)
                topicRepository.deleteById(id)
                return topic
            }
            else -> {
                throw ExceptionInInitializerError("Denied")
            }
        }
    }

    override fun getTopicsByAuthorId(userId: String): List<TopicVO> {
        TODO("Not yet implemented")
    }

    override fun getTopicsByContributors(userId: String): List<TopicVO> {
        TODO("Not yet implemented")
    }

    override fun toVO(topic: Topic): TopicVO {
        return CreatorTopicVO.fromData(topic) as TopicVO
    }

}
