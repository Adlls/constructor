package digital.tutors.constructor.services

import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.Topic
import digital.tutors.constructor.vo.CreatorVO
import digital.tutors.constructor.vo.VO
import digital.tutors.constructor.vo.progress.ProgressVO
import digital.tutors.constructor.vo.topic.CreateRqTopic
import digital.tutors.constructor.vo.topic.TopicVO
import digital.tutors.constructor.vo.topic.UpdateRqTopic
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import javax.swing.text.html.parser.Entity

interface TopicService {

    @Throws(EntityNotFoundException::class)
    fun getTopicById(id: String, userId: String): TopicVO

    @Throws(EntityNotFoundException::class)
    fun getAllTopics(pageable: Pageable, userId: String, courseId: String): Page<TopicVO>

    fun createTopic(createRqTopic: CreateRqTopic, userId: String): TopicVO

    @Throws(EntityNotFoundException::class)
    fun updateTopic(id: String, updateRqTopic: UpdateRqTopic, userId: String): TopicVO

    @Throws(EntityNotFoundException::class)
    fun deleteTopic(id: String, userId: String): TopicVO

    //@Throws(EntityNotFoundException::class)
    //fun getProgressForCurrentTopic(): ProgressVO

    @Throws(EntityNotFoundException::class)
    fun getTopicsByAuthorId(userId: String): List<TopicVO>

    fun getTopicsByContributors(userId: String): List<TopicVO>

    fun toVO(topic: Topic): TopicVO
}
