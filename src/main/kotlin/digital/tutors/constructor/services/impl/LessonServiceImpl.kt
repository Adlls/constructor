package digital.tutors.constructor.services.impl

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.services.impl.UserServiceImpl
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.Course
import digital.tutors.constructor.entities.Lesson
import digital.tutors.constructor.entities.Level
import digital.tutors.constructor.entities.Topic
import digital.tutors.constructor.repositories.LessonRepository
import digital.tutors.constructor.services.LessonService
import digital.tutors.constructor.vo.VO
import digital.tutors.constructor.vo.lesson.CreateRqLesson
import digital.tutors.constructor.vo.lesson.CreatorLessonVO
import digital.tutors.constructor.vo.lesson.LessonVO
import digital.tutors.constructor.vo.lesson.UpdateRqLesson
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import javax.swing.text.html.parser.Entity

@Service
class LessonServiceImpl: LessonService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Autowired
    lateinit var lessonRepository :LessonRepository


    @Throws(EntityNotFoundException::class)
    override fun getAllLessons(pageable: Pageable): Page<LessonVO> {
        return lessonRepository.findAll(pageable).map(::toVO)
    }

    @Throws(EntityNotFoundException::class)
    override fun getLessonById(id: String): LessonVO {
        return lessonRepository.findById(id).map(::toVO).orElseThrow {
            throw EntityNotFoundException("Lesson with id $id not found")
        }
    }

    @Throws(EntityNotFoundException::class)
    override fun createLesson(createRqLesson: CreateRqLesson): LessonVO {
        val id = lessonRepository.save(Lesson().apply {
            author = User(id = createRqLesson.author?.id)
            topic = Topic(id = createRqLesson.topic?.id)
            relations = createRqLesson.relations
            levels = createRqLesson.levels.map { Level(it.id) }
        }).id ?: throw IllegalArgumentException("Bad id request")
        log.debug("Created lesson $id")
        return getLessonById(id)
    }

    @Throws(EntityNotFoundException::class)
    override fun updateLesson(id: String, updateRqLesson: UpdateRqLesson): LessonVO {
        lessonRepository.save(lessonRepository.findById(id).get().apply {
            topic = Topic(id = updateRqLesson.topic?.id)
            relations = updateRqLesson.relations
            levels = updateRqLesson.levels
        })
        log.debug("Updated lesson $id")
        return getLessonById(id)
    }

    @Throws(EntityNotFoundException::class)
    override fun deleteLesson(id: String): LessonVO {
        val lesson = getLessonById(id)
        lessonRepository.deleteById(id)
        return lesson
    }

    override fun toVO(lesson: Lesson): LessonVO {
        return CreatorLessonVO.fromData(lesson) as LessonVO
    }


}
