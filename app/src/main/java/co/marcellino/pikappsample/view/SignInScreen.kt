package co.marcellino.pikappsample.view

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignInScreen(
    onSignIn: (String, String) -> Unit,
    navigateSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }

    var password by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(true) }

    val isSignInEnabled: Boolean =
        (isEmailValid && (email != "") && isPasswordValid && (password != ""))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign In")

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = ((email != "") && (Patterns.EMAIL_ADDRESS.matcher(email).matches()))
            },
            isError = !isEmailValid,

            label = { Text("Email") },
            modifier = Modifier.padding(top = 32.dp)
        )

        OutlinedTextField(
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                password = it
                isPasswordValid = (password != "")
            },
            isError = !isPasswordValid,

            label = { Text("Password") },
            modifier = Modifier.padding(top = 8.dp)
        )

        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = {
                Log.d("SignInScreen", "Password retrieval system not implemented!")
            },

            modifier = Modifier.padding(top = 16.dp),
        )

        Button(
            onClick = { onSignIn(email, password) },
            enabled = isSignInEnabled,

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            Text("Sign In")
        }

        Text("or", modifier = Modifier.padding(top = 16.dp))

        Button(
            onClick = navigateSignUp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Sign Up")
        }
    }
}