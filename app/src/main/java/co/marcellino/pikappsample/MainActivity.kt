package co.marcellino.pikappsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.marcellino.pikappsample.view.SignInScreen
import co.marcellino.pikappsample.view.SignUpScreen
import co.marcellino.pikappsample.view.WelcomeScreen
import co.marcellino.pikappsample.viewmodel.UserViewModel
import kotlinx.coroutines.delay
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

    val focusManager = LocalFocusManager.current

    var isSnackBarVisible by remember { mutableStateOf(false) }
    var snackBarText by remember { mutableStateOf("") }

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
                            focusManager.clearFocus()

                            isSnackBarVisible = true
                            snackBarText = "Signing in, loading..."
                            Log.d("SignInScreen", "Signing in, loading...")

                            if (userViewModel.attemptSignIn(email = email, password = password)) {
                                isSnackBarVisible = false
                                Log.d("SignInScreen", "User signed in!")

                                navController.navigate("Welcome") {
                                    popUpTo("SignIn") { inclusive = true }
                                }
                            } else {
                                snackBarText = "Failed to sign in!"
                                Log.d("SignInScreen", "Failed to sign in!")
                            }

                            delay(2000)
                            isSnackBarVisible = false
                        }
                    },
                    navigateSignUp = { navController.navigate("SignUp") }
                )
            }

            composable("SignUp") {
                SignUpScreen(onSignUp = { email, password ->
                    composableScope.launch {
                        focusManager.clearFocus()

                        isSnackBarVisible = true
                        snackBarText = "Signing up, loading..."
                        Log.d("SignUpScreen", "Signing up, loading...")

                        if (userViewModel.attemptSignUp(email = email, password = password)) {
                            isSnackBarVisible = false
                            Log.d("SignUpScreen", "User created!")

                            navController.navigate("SignIn") {
                                popUpTo("SignUp") { inclusive = true }
                            }
                        } else {
                            snackBarText = "Failed to sign up!"
                            Log.d("SignUpScreen", "Failed to sign up!")
                        }

                        delay(2000)
                        isSnackBarVisible = false
                    }
                })
            }

            composable("Welcome") {
                WelcomeScreen(user = userViewModel.currentUser!!)
            }
        }

        if (isSnackBarVisible) Snackbar { Text(snackBarText) }
    }
}