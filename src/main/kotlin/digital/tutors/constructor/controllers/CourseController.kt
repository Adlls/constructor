package digital.tutors.constructor.controllers

import digital.tutors.constructor.core.auth.AuthorizationService
import digital.tutors.constructor.core.controller.BaseController
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.services.CourseService
import digital.tutors.constructor.vo.course.CourseVO
import digital.tutors.constructor.vo.course.CreateRqCourse
import digital.tutors.constructor.vo.course.UpdateRqCourse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping
class CourseController: BaseController() {

    @Autowired
    lateinit var courseService :CourseService

    @Autowired
    lateinit var authorizationService :AuthorizationService

    @GetMapping("/courses")
    fun getCourses(@RequestParam page: Int): ResponseEntity<Page<CourseVO>> {
      return  processServiceExceptions {
            try {
                val pageRequest = PageRequest.of(page, 10)
                ResponseEntity.ok(courseService.getAllCourses(pageRequest))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Courses not found", ex)
            }
        }
    }

    @GetMapping("/course/{id}")
    fun getCourseById(@PathVariable id: String): ResponseEntity<CourseVO> {
        return processServiceExceptions {
            try {
                ResponseEntity.ok(courseService.getCourseById(id))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Course with id $id not found", ex)
            }
        }
    }

    @PostMapping("/course")
    fun createCourse(@RequestBody createRqCourse: CreateRqCourse): ResponseEntity<CourseVO> {
        return processServiceExceptions {
                ResponseEntity.ok(courseService.createCourse(createRqCourse))
        }
    }

    @PutMapping("/course/{id}")
    fun updateCourse(@PathVariable id :String, @RequestBody updateRqCourse: UpdateRqCourse): ResponseEntity<CourseVO> {
        return processServiceExceptions {
            try {
                ResponseEntity.ok(courseService.updateCourse(id, updateRqCourse))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Course with id $id not found", ex)
            }
        }
    }

    @DeleteMapping("/course/{id}")
    fun deleteCourse(@PathVariable id: String): ResponseEntity<CourseVO> {
        return processServiceExceptions {
            try {
                ResponseEntity.ok(courseService.delete(id))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Course with id $id not found", ex)
            }
        }
    }

}
