package co.marcellino.pikappsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.marcellino.pikappsample.view.SignInScreen
import co.marcellino.pikappsample.view.SignUpScreen
import co.marcellino.pikappsample.view.WelcomeScreen
import co.marcellino.pikappsample.viewmodel.UserViewModel
import kotlinx.coroutines.launch

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
    val composableScope = rememberCoroutineScope()

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "SignIn",

            modifier = Modifier.padding(horizontal = 80.dp, vertical = 48.dp)
        ) {
            composable("SignIn") {
                SignInScreen(
                    onSignIn = { email, password ->
                        composableScope.launch {
                            Log.d("SignInScreen", "Signing in, loading...")

                            if (userViewModel.attemptSignIn(email = email, password = password)) {
                                Log.d("SignInScreen", "User signed in!")
                                navController.navigate("Welcome")
                            } else {
                                Log.d("SignInScreen", "Failed to sign in!")
                            }
                        }
                    },
                    navigateSignUp = { navController.navigate("SignUp") }
                )
            }

            composable("SignUp") {
                SignUpScreen(
                    onSignUp = { email, password ->
                        composableScope.launch {
                            Log.d("SignUpScreen", "Signing up, loading...")

                            if (userViewModel.attemptSignUp(email = email, password = password)) {
                                Log.d("SignUpScreen", "User created!")
                                navController.navigate("SignIn")
                            } else {
                                Log.d("SignUpScreen", "Failed to sign up!")
                            }
                        }
                    }
                )
            }

            composable("Welcome") {
                WelcomeScreen(user = userViewModel.currentUser!!)
            }
        }
    }
}