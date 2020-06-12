package digital.tutors.constructor.services

import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.entities.BodyLevel
import digital.tutors.constructor.entities.Levels
import digital.tutors.constructor.vo.level.LevelVO

interface LevelService {

    fun createLevel(idLesson: String, bodyLevel: BodyLevel): LevelVO

    @Throws(EntityNotFoundException::class)
    fun updateLevel(): LevelVO

    @Throws(EntityNotFoundException::class)
    fun findLevelById(id: String): LevelVO

    fun toLevelVO(levels: Levels): LevelVO

}
