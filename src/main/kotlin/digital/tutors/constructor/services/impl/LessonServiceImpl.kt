package digital.tutors.constructor.services.impl

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.services.impl.UserServiceImpl
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.*
import digital.tutors.constructor.repositories.LessonRepository
import digital.tutors.constructor.services.LessonService
import digital.tutors.constructor.services.LevelService
import digital.tutors.constructor.services.ProgressService
import digital.tutors.constructor.services.TopicService
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

@Service
class LessonServiceImpl: LessonService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Autowired
    lateinit var lessonRepository: LessonRepository

    @Autowired
    lateinit var progressService: ProgressService

    @Autowired
    lateinit var levelService: LevelService

    @Autowired
    lateinit var topicService: TopicService

    fun createProgressForFollowersByLesson(lessonId: String) {
         /*
             TODO: Создаем для каждого фолловера (студента) свою таблицу
                   с прогрессами разных занятий одного курса (пока пустым)
         */
        val lesson = lessonRepository.findLessonById(lessonId)
        val topic = lesson.topic
        val course = topic?.course

        val lessons = mutableListOf(LessonProgress().apply {
            this.lesson = Lesson(id = lessonId)
        })
        course?.followers?.forEach {
            progressService.createLessonProgress(lessons, it.id.toString())

        }
    }

    fun createLevel(idLesson: String, level: BodyLevel) {
        val idLevels = levelService.createLevel(idLesson, level).id
       // level.htmlBody?.let { levelService.addLevel(idLesson, it, 2) }
       // level.htmlBody?.let { levelService.addLevel(idLesson, it, 3) }
        lessonRepository.save(lessonRepository.findById(idLesson).get().apply {
            levels = Levels(id = idLevels)
        })
    }

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

    override fun getLessonsByCourseId(idCourse: String): List<LessonVO> {
        //var lessons: List<LessonVO>
        var topicId = topicService.getTopicByCourseId(idCourse).id
        println(topicId);
        var lessons = topicId?.let { lessonRepository.findLessonsByTopicId(it).map(::toVO) }

        return lessons!!
    }

    @Throws(EntityNotFoundException::class)
    override fun createLesson(createRqLesson: CreateRqLesson): LessonVO {
        val id = lessonRepository.save(Lesson().apply {
            author = User(createRqLesson.author?.id)
            topic = Topic(createRqLesson.topic?.id)
            relations = createRqLesson.relations
        }).id ?: throw IllegalArgumentException("Bad id request")
        log.debug("Created lesson $id")
        //println(createRqLesson.topic?.id)
        /* TODO: Поумолчанию создаем прогресс */
        createProgressForFollowersByLesson(id)
        /* TODO: Поумолчанию создаем уровень */
        val level = BodyLevel().apply {
            htmlBody = "<body> ... </body>"
            numLevel = 1
        }


        createLevel(id, level)
        levelService.addLevel(id, level.htmlBody, 2);
        levelService.addLevel(id, level.htmlBody, 3);
        return getLessonById(id)
    }

    @Throws(EntityNotFoundException::class)
    override fun updateLesson(id: String, updateRqLesson: UpdateRqLesson): LessonVO {

        lessonRepository.save(lessonRepository.findById(id).get().apply {
            topic = Topic(id = updateRqLesson.topic?.id)
            relations = updateRqLesson.relations
            levels = Levels(id = updateRqLesson.levels?.id)
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
