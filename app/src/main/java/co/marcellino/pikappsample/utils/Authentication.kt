package co.marcellino.pikappsample.utils

import co.marcellino.pikappsample.model.User
import kotlinx.coroutines.delay

object Authentication {
    private val registeredUsers = mutableListOf(
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

    suspend fun signUp(email: String, password: String): Boolean {
        delay(2000)

        registeredUsers.forEach {
            if (email == it.email) return false
        }

        registeredUsers.add(User(email = email, password = password))
        return true
    }
}