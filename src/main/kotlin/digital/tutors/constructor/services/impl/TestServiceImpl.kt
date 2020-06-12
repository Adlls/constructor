package digital.tutors.constructor.services.impl

import digital.tutors.constructor.services.TestService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class TestServiceImpl: TestService {


    override fun getResultByLessonId(answer: Int, lessonId: String): Boolean {
        /*
            TODO: Эмитация получение результата теста по id занятия.
                  Модуль тестирования находиться отдельно от этого проекта.
                  Выводим рандом true или false.
        */

        return Random.nextInt(0,2) == 1
    }

}
