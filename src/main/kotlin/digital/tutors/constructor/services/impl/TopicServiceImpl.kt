package digital.tutors.constructor.services.impl

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.services.impl.UserServiceImpl
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.*
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
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class TopicServiceImpl: TopicService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Autowired
    lateinit var topicRepository: TopicRepository

    @Throws(EntityNotFoundException::class)
    override fun getTopicById(id: String): TopicVO {
       return topicRepository.findById(id).map(::toVO).orElseThrow {
            throw EntityNotFoundException("Topic not found with $id")
        }
    }

    @Throws(EntityNotFoundException::class)
    override fun getAllTopics(pageable: Pageable): Page<TopicVO> {
        return topicRepository.findAll(pageable).map(::toVO)
    }

    override fun createTopic(createRqTopic: CreateRqTopic): TopicVO {
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

        return getTopicById(id)
    }
    @Throws(EntityNotFoundException::class)
    override fun updateTopic(id: String, updateRqTopic: UpdateRqTopic): TopicVO {
        topicRepository.save(topicRepository.findById(id).get().apply {
            /*
            title = updateRqTopic.title
            tags = updateRqTopic.tags

            fgos = updateRqTopic.fgos
            profStandard = updateRqTopic.profStandard
            relations = updateRqTopic.relations

             */
        })
        log.debug("Updated topic $id")
        return getTopicById(id)
    }

    @Throws(EntityNotFoundException::class)
    override fun deleteTopic(id: String): TopicVO {
        val topic = getTopicById(id)
        topicRepository.deleteById(id)
        return topic
    }

    override fun toVO(topic: Topic): TopicVO {
        return CreatorTopicVO.fromData(topic) as TopicVO
    }

}
