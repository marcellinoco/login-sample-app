package co.marcellino.pikappsample.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.marcellino.pikappsample.model.User
import co.marcellino.pikappsample.utils.Authentication

class UserViewModel : ViewModel() {
    var currentUser by mutableStateOf<User?>(null)

    fun setUser(user: User?) {
        currentUser = user
    }

    fun attemptSignIn(email: String, password: String): Boolean {
        return if (Authentication.signIn(email = email, password = password)) {
            currentUser = User(email = email, password = password)
            true
        } else false
    }
}