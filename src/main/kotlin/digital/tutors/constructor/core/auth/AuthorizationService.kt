package digital.tutors.constructor.core.auth

import java.util.*

interface AuthorizationService {

    fun currentUserIdOrDie(): String

    fun currentUserId(): Optional<String>

    fun getUserIdFromToken(token: String?): String?

}
