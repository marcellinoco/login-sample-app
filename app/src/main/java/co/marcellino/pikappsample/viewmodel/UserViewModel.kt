package co.marcellino.pikappsample.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.marcellino.pikappsample.model.User
import co.marcellino.pikappsample.utils.Authentication

class UserViewModel : ViewModel() {
    var currentUser by mutableStateOf<User?>(null)

    private fun setUser(user: User?) {
        currentUser = user
    }

    suspend fun attemptSignIn(email: String, password: String): Boolean {
        return if (Authentication.signIn(email = email, password = password)) {
            setUser(User(email = email, password = password))
            true
        } else false
    }

    suspend fun attemptSignUp(email: String, password: String): Boolean {
        return if (Authentication.signUp(email = email, password = password)) {
            setUser(User(email = email, password = password))
            true
        } else false
    }
}