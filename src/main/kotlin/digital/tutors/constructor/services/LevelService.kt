package digital.tutors.constructor.services

import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.vo.level.LevelVO

interface LevelService {

    @Throws(EntityNotFoundException::class)
    fun createLevels(): LevelVO

    @Throws(EntityNotFoundException::class)
    fun updateLevel(): LevelVO
}
