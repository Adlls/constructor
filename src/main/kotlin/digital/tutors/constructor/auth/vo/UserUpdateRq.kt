package digital.tutors.constructor.auth.vo

data class UserUpdateRq(
        val firstName: String?,
        val lastName: String?,
        val confirmed: Boolean?
)
