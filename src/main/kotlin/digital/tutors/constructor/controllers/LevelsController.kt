package digital.tutors.constructor.controllers

import digital.tutors.constructor.core.controller.BaseController
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.services.LevelService
import digital.tutors.constructor.vo.lesson.LessonVO
import digital.tutors.constructor.vo.lesson.UpdateRqLesson
import digital.tutors.constructor.vo.level.LevelVO
import digital.tutors.constructor.vo.level.UpdateRqLevels
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping
class LevelsController: BaseController() {
    @Autowired
    lateinit var levelService: LevelService

    @PutMapping("/level/{id}")
    fun updateLesson(@PathVariable id: String, @RequestBody updateRqLevels: UpdateRqLevels): ResponseEntity<LevelVO> {
        try {
            return processServiceExceptions {
                ResponseEntity.ok(levelService.updateLevels(id, updateRqLevels))
            }
        } catch (ex: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Level with id $id not found", ex)
        }
    }
}
