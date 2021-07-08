package co.marcellino.pikappsample.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import co.marcellino.pikappsample.model.User

@Composable
fun WelcomeScreen(
    user: User
) {
    Text("Hai ${user.email}")
}