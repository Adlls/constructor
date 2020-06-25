package digital.tutors.constructor.services.impl

import digital.tutors.constructor.auth.entities.User
import digital.tutors.constructor.auth.repositories.UserRepository
import digital.tutors.constructor.auth.services.impl.UserServiceImpl
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.*
import digital.tutors.constructor.repositories.*
import digital.tutors.constructor.services.LessonService
import digital.tutors.constructor.services.LevelService
import digital.tutors.constructor.services.ProgressService
import digital.tutors.constructor.services.TestService
import digital.tutors.constructor.vo.progress.CreatorProgressVO
import digital.tutors.constructor.vo.progress.ProgressVO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
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
    lateinit var testService: TestService

    @Autowired
    lateinit var levelRepository: LevelRepository

    @Autowired
    lateinit var lessonRepository: LessonRepository

    @Autowired
    lateinit var topicRepository: TopicRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var courseServiceImpl: CourseServiceImpl

    @Autowired
    lateinit var lessonService: LessonService


    override fun getProgressByLessonId(idLesson: String): List<ProgressVO> {
        TODO("Not yet implemented")
        /*
        val lesson = lessonRepository.findLessonById(idLesson)
        return progressRepository.findAllByLessonProgressLesson(lesson).map(::toProgressVO)
        */
    }



    override fun getProgressById(idProgress: String): ProgressVO {
        return progressRepository.findById(idProgress).map(::toProgressVO).orElseThrow {
            throw EntityNotFoundException("Not found progress with id $idProgress")
        }
    }

    override fun getProgress(pageable: Pageable): Page<ProgressVO> {
        TODO("Not yet implemented")
    }

    override fun createLessonProgress(lessonProgress: List<LessonProgress>, userId: String): ProgressVO {
        return if (progressRepository.existsProgressByStudentId(userId)) {
            val id =  progressRepository.save(progressRepository.findByStudentId(userId).get().apply {
                this.lessonProgress = this.lessonProgress?.plus(lessonProgress)
            }).id?: throw IllegalArgumentException("Bad request")
            getProgressById(id)
        } else {
            val id = progressRepository.save(Progress().apply {
                this.lessonProgress = lessonProgress
                student = User(id = userId)
            }).id?: throw IllegalArgumentException("Bad request")
            getProgressById(id)
        }
    }

    override fun getProgressByCourseId(courseId: String): List<ProgressVO> {
        var lessons = lessonService.getLessonsByCourseId(courseId)
        var progress: List<ProgressVO> = progressRepository.findAll().map(::toProgressVO)
        var resultProgress = arrayListOf<ProgressVO>()

        progress.forEach {
           for (lessonProgress in it.lessonProgress!!) {
               for (lesson in lessons) {
                   if (lessonProgress.lesson?.id == lesson.id) {
                       resultProgress.add(it)
                   }
               }
            }
        }
        print(resultProgress)
        return resultProgress
    }

    override fun getProgressByStudentId(idStudent: String): List<ProgressVO> {
        return progressRepository.findAllByStudentId(idStudent).map(::toProgressVO)
    }

    override fun updateProgress(idLesson: String, userId: String, answerTest: Int): ProgressVO {

        val progress = progressRepository.findByStudentId(userId).orElseThrow {
           throw EntityNotFoundException("Not found progress by student id $userId")
       }
        val lesson = lessonRepository.findById(idLesson).orElseThrow {
            throw EntityNotFoundException("Not found lesson by id $idLesson")
        }
        val level = lesson.levels

        val maxLevel = level?.bodyLevels?.size

        val resultTestByLesson = testService.getResultByLessonId(answerTest, idLesson)

        progress.lessonProgress?.forEach {
           if (idLesson == it.lesson?.id) {
               if (resultTestByLesson)  {
                   it.completed = true
                }
                else if (!resultTestByLesson) {
                   if (it.currentLevel != maxLevel) {
                       it.currentLevel = it.currentLevel?.plus(1)
                   }
                }
           }
           else {
               println("lesson is not found")
               throw EntityNotFoundException("Not found lesson $idLesson in progress ${progress.id}")
           }
        }

        progressRepository.save(progress)
        log.debug("updated progress by lesson id $idLesson")
        return getProgressById(progress.id.toString())

    }

    override fun deleteProgress(idProgress: String): ProgressVO {
        TODO("Not yet implemented")
    }

    override fun toProgressVO(progress: Progress): ProgressVO {
        return CreatorProgressVO.fromData(progress) as ProgressVO
    }
}
