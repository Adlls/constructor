package digital.tutors.constructor.controllers

import digital.tutors.constructor.core.controller.BaseController
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.services.ProgressService
import digital.tutors.constructor.vo.lesson.LessonVO
import digital.tutors.constructor.vo.progress.ProgressVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping
class ProgressController: BaseController() {
    @Autowired
    lateinit var progressService: ProgressService

    @GetMapping("/progress/{courseId}")
    fun getProgressByCourseId(@PathVariable courseId: String): ResponseEntity<List<ProgressVO>> {
        return processServiceExceptions {
            try {
                ResponseEntity.ok(progressService.getProgressByCourseId(courseId))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Progress or Course not found", ex)
            }
        }
    }
}
