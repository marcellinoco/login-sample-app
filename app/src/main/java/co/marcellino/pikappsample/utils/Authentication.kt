package co.marcellino.pikappsample.utils

import co.marcellino.pikappsample.model.User

object Authentication {
    private val registeredUsers = listOf(
        User("test@test.com", "test123"),
        User("admin@admin.com", "admin")
    )

    fun signIn(email: String, password: String): Boolean {
        registeredUsers.forEach {
            if (email == it.email && password == it.password) return true
        }

        return false
    }
}