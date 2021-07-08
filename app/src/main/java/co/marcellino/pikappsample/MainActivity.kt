package co.marcellino.pikappsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.marcellino.pikappsample.view.SignInScreen
import co.marcellino.pikappsample.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseAppScreen(userViewModel = userViewModel)
        }
    }
}

@Composable
fun BaseAppScreen(userViewModel: UserViewModel) {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController = navController, startDestination = "SignIn") {
            composable("SignIn") {
                SignInScreen(
                    onSignIn = { email, password ->
                        if (userViewModel.attemptSignIn(email = email, password = password))
                            navController.navigate("Welcome")
                    },
                    navigateSignUp = { navController.navigate("SignUp") }
                )
            }
        }
    }
}