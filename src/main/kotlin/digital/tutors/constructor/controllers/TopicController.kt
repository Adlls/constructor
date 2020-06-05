package digital.tutors.constructor.controllers

import digital.tutors.constructor.core.controller.BaseController
import digital.tutors.constructor.services.TopicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class TopicController: BaseController() {

    @Autowired
    lateinit var topicService: TopicService

}
