package digital.tutors.constructor.services.impl

import digital.tutors.constructor.auth.services.impl.UserServiceImpl
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.BodyLevel
import digital.tutors.constructor.entities.Lesson
import digital.tutors.constructor.entities.Levels
import digital.tutors.constructor.entities.Topic
import digital.tutors.constructor.repositories.LevelRepository
import digital.tutors.constructor.services.LevelService
import digital.tutors.constructor.vo.level.CreatorLevelVO
import digital.tutors.constructor.vo.level.LevelVO
import digital.tutors.constructor.vo.level.UpdateRqLevels
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LevelServiceImpl: LevelService {

    private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Autowired
    lateinit var levelRepository: LevelRepository

    override fun createLevel(idLesson: String, bodyLevel: BodyLevel): LevelVO {

        //если такие уровни уже существуют для текущего заняти
        if (levelRepository.existsByLessonId(idLesson)) {
            val level = levelRepository.findLevelByLessonId(idLesson)
           //проверяем есть ли такой уровень уже, чтобы не дублировать одно и то же а обновлять уже то, что есть
            level.bodyLevels?.forEach {
                if (it.numLevel == bodyLevel.numLevel) {
                    it.htmlBody = bodyLevel.htmlBody
                    levelRepository.save(level)
                    log.debug("Updated level by ${level.id}")
                    return findLevelById(level.id.toString())
                }
            }
            //если уровней таких еще нет, добавляем новый уровень
            val id = levelRepository.save(levelRepository.findByLessonId(idLesson).get().apply {
                this.bodyLevels = this.bodyLevels?.plus(bodyLevel)
            }).id ?: throw IllegalArgumentException("Bad id request")
            log.debug("Updated level by id $id")
            return findLevelById(id)
        } else {
            val id = levelRepository.save(Levels().apply {
                this.bodyLevels = mutableListOf(bodyLevel)
                lesson = Lesson(idLesson)
            }).id ?: throw IllegalArgumentException("Bad id request")
            log.debug("created level for lesson id $idLesson")
            return findLevelById(id)
        }
    }

    override fun addLevel(idLesson: String, htmlBody: String?, numLevel: Int) {
        val level = levelRepository.findByLessonId(idLesson).orElseThrow {
            throw EntityNotFoundException("Not found level with lesson id $idLesson")
        }

        val bodyLevel = BodyLevel().apply {
            this.htmlBody = htmlBody
            this.numLevel = numLevel
        }

        (level.bodyLevels as ArrayList<BodyLevel>).add(bodyLevel)
        levelRepository.save(level)
    }

    override fun disenableLevel(idLesson: String, numLevel: Int) {
        val level = levelRepository.findByLessonId(idLesson).orElseThrow {
            throw EntityNotFoundException("Not found level with lesson id $idLesson")
        }
        level.bodyLevels?.forEach {
            if (it.numLevel == numLevel) {
                it.enabled = false
            }
        }
        levelRepository.save(level)
    }

    override fun updateLevel(idLesson: String, numLevel: Int, htmlBody: String): LevelVO {
        val level = levelRepository.findByLessonId(idLesson).orElseThrow {
            throw EntityNotFoundException("Not found level with lesson id $idLesson")
        }

        level.bodyLevels?.forEach {
            if (it.numLevel == numLevel) it.htmlBody = htmlBody
        }
        levelRepository.save(level)

        return findLevelById(level.id.toString())
    }

    override fun updateLevels(id: String, updateRqLevels: UpdateRqLevels): LevelVO {
        levelRepository.save(levelRepository.findById(id).get().apply {
            bodyLevels =  updateRqLevels.bodyLevels
        })
        log.debug("Updated levels $id")
        return findLevelById(id)
    }

    override fun findLevelById(id: String): LevelVO {
        return levelRepository.findById(id).map(::toLevelVO).orElseThrow {
            throw EntityNotFoundException("Not found level with id $id")
        }
    }

    override fun toLevelVO(levels: Levels): LevelVO {
        return CreatorLevelVO.fromData(levels) as LevelVO
    }

}
