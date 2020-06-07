package digital.tutors.constructor.controllers

import digital.tutors.constructor.core.controller.BaseController
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.services.LessonService
import digital.tutors.constructor.vo.lesson.CreateRqLesson
import digital.tutors.constructor.vo.lesson.LessonVO
import digital.tutors.constructor.vo.lesson.UpdateRqLesson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.awt.print.Pageable


@RestController
@RequestMapping
class LessonController: BaseController() {

    @Autowired
    lateinit var lessonService: LessonService

    @GetMapping("/lessons")
    fun getLessons(@RequestParam page: Int): ResponseEntity<Page<LessonVO>> {
        return processServiceExceptions {
            try {
                val pageRequest = PageRequest.of(page, 10)
                ResponseEntity.ok(lessonService.getAllLessons(pageRequest))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Lessons not found", ex)
            }
        }
    }

    @GetMapping("/lesson/{id}")
    fun getLessonById(@PathVariable id: String): ResponseEntity<LessonVO> {
        return processServiceExceptions {
            try {
                ResponseEntity.ok(lessonService.getLessonById(id))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson with id $id not found", ex)
            }
        }
    }

    @PostMapping("/lesson")
    fun createLesson(@RequestBody createRqLesson: CreateRqLesson): ResponseEntity<LessonVO> {
        return processServiceExceptions {
            ResponseEntity.ok(lessonService.createLesson(createRqLesson))
        }
    }

    @PutMapping("/lesson/{id}")
    fun updateLesson(@PathVariable id: String, @RequestBody updateRqLesson: UpdateRqLesson): ResponseEntity<LessonVO> {
        try {
            return processServiceExceptions {
                ResponseEntity.ok(lessonService.updateLesson(id, updateRqLesson))
            }
        } catch (ex: EntityNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson with id $id not found", ex)
        }
    }

    @DeleteMapping("/lesson/{id}")
    fun deleteLesson(@PathVariable id: String): ResponseEntity<LessonVO> {
        return processServiceExceptions {
            try {
                ResponseEntity.ok(lessonService.deleteLesson(id))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson with id $id not found", ex)
            }
        }
    }
}
