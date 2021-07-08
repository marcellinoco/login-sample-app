package co.marcellino.pikappsample.utils

import co.marcellino.pikappsample.model.User
import kotlinx.coroutines.delay

object Authentication {
    private val registeredUsers = listOf(
        User("test@test.com", "test123"),
        User("admin@admin.com", "admin")
    )

    suspend fun signIn(email: String, password: String): Boolean {
        delay(2000)

        registeredUsers.forEach {
            if (email == it.email && password == it.password) return true
        }
        return false
    }
}