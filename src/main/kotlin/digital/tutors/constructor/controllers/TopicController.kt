package digital.tutors.constructor.controllers

import digital.tutors.constructor.core.controller.BaseController
import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.services.TopicService
import digital.tutors.constructor.vo.topic.CreateRqTopic
import digital.tutors.constructor.vo.topic.TopicVO
import digital.tutors.constructor.vo.topic.UpdateRqTopic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping
class TopicController: BaseController() {

    @Autowired
    lateinit var topicService: TopicService

    @GetMapping("/topics")
    fun getTopics(@RequestParam page: Int): ResponseEntity<Page<TopicVO>> {
        return processServiceExceptions {
            try {
                val pageRequest = PageRequest.of(page, 10)
                ResponseEntity.ok(topicService.getAllTopics(pageRequest))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Topics not found", ex)
            }
        }
    }

    @GetMapping("/topic/{id}")
    fun getTopicById(@PathVariable id: String): ResponseEntity<TopicVO> {
        return processServiceExceptions {
            try {
                ResponseEntity.ok(topicService.getTopicById(id))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Topic with id $id not found", ex)
            }
        }
    }

    @PostMapping("/topic")
    fun createTopic(@RequestBody createRqTopic: CreateRqTopic): ResponseEntity<TopicVO> {
        return processServiceExceptions {
                ResponseEntity.ok(topicService.createTopic(createRqTopic))
        }
    }

    @PutMapping("/topic/{id}")
    fun updateTopic(@PathVariable id: String, @RequestBody updateRqTopic: UpdateRqTopic): ResponseEntity<TopicVO> {
        return processServiceExceptions {
            try {
                ResponseEntity.ok(topicService.updateTopic(id, updateRqTopic))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Topic with id $id not found", ex)
            }
        }
    }

    @DeleteMapping("topic/{id}")
    fun deleteTopic(@PathVariable id: String): ResponseEntity<TopicVO> {
        return processServiceExceptions {
            try {
                ResponseEntity.ok(topicService.deleteTopic(id))
            } catch (ex: EntityNotFoundException) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Topic with id $id not found", ex)
            }
        }
    }



}
