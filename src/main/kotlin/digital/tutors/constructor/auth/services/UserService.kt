package digital.tutors.constructor.auth.services

import digital.tutors.constructor.core.exception.EntityNotFoundException
import digital.tutors.constructor.auth.vo.UserCreateRq
import digital.tutors.constructor.auth.vo.UserLoginRq
import digital.tutors.constructor.auth.vo.UserVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {

    @Throws(EntityNotFoundException::class)
    fun getUserByIdOrThrow(id: String): UserVO

    @Throws(EntityNotFoundException::class)
    fun getUsers(pageable: Pageable): Page<UserVO>

    fun createUser(user: UserCreateRq): UserVO

    fun loginUser(user: UserLoginRq): UserVO

    fun existsByEmail(email: String): Boolean

//    @Throws(EntityNotFoundException::class)
//    fun updateUser(id: String, page: PageUpdateRq): PageVO

    @Throws(EntityNotFoundException::class)
    fun delete(id: String)

}
