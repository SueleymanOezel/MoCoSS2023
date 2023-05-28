package com.example.navigationdrawer.Login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigationdrawer.R
import com.example.navigationdrawer.Login.SignUpState
import com.example.navigationdrawer.ui.theme.NavigationDrawerTheme
import com.example.navigationdrawer.ui.theme.Screens.LoginScreen

import kotlin.math.log



@Composable
fun LoginPageScreen(

    // navController: NavHostController,
    loginViewModel: LoginViewModel? = null,
    onNavToMainScreen: () -> Unit,
    onNavToSignUpPage: () -> Unit,
) {
    // create an instance of loginUi
    val loginUiState = loginViewModel?.loginUiState

    // to check if there is an error
    val isError = loginUiState?.loginError != null

    val context = LocalContext.current

    Surface(
        color = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.onSurface,
    ) {

        // Formular für den Login-Bildschirm
        val (username, onUserNameChange) = remember {
            mutableStateOf("")
        }

        val (password, onPasswordChange) = remember {
            mutableStateOf("")
        }

        val (checked, onCheckedChange) = remember {
            // Zustand für die Checkbox "Remember me"
            mutableStateOf(false)
        }

        Column {
            // Überschrift "Login"
            Text(
                text = "Login",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(20.dp),
            )

            if (isError) {
                Text(
                    text = loginUiState?.loginError ?: "unknown error", color = Color.Red
                )
            }

            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )

            // Eingabefeld für den Benutzernamen
            OutlinedTextField(
                /* modifier = Modifier
                     .fillMaxWidth()
                     .padding (16.dp),*/

                // wenn UserName null ist, soll leerer Bildschrim ausgegeben werden
                value = loginUiState?.userName ?: "",
                onValueChange = { loginViewModel?.onUserNameChange(it) },
                label = {
                    Text(text = "UserName") //E-Mail
                },
                isError = isError,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )

            // Eingabefeld für das Passwort
            OutlinedTextField(
                value = loginUiState?.password ?: "",
                onValueChange = { loginViewModel?.onPasswordNameChange(it)},
                label = {
                    Text(text = "Password")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                isError = isError,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )

            // Checkbox "Remember me"
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = "Remember me")
                }

                // Button "Forgot Password"
                TextButton(
                    onClick = {  },
                ) {
                    Text(text = "Forgot Password")
                }

            }

            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )

            // Button "Login"
            Button(
                // beim Anklicken des Buttons wird man auf den Mainscreen weitergeleitet
                onClick = { loginViewModel?.loginUser(context)},
                //{ navController.navigate(Screen.Main.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Login")
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,

            ) {
            Text(text = "Don't have an Account?")

            Spacer(
                modifier = Modifier
                    .size(8.dp)
            )

            // Button "Sign Up"
            TextButton(
                onClick = { onNavToSignUpPage.invoke() },
            ) {
                Text(text = "Sign Up")

            }


        }

        if (loginUiState?.isLoading == true) {
            CircularProgressIndicator()
        }

        //when the user is successfully launched in, we want to navigate to the Mainscreen (homepage)
        LaunchedEffect(key1 = loginViewModel?.hasUser) {
            if (loginViewModel?.hasUser == true) {
                onNavToMainScreen.invoke()
            }
        }


    }


}

@Composable
fun SignUpPageScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToMainScreen:() -> Unit,
    onNavToLoginPage:() -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.primary
        )

        if (isError){
            Text(
                text = loginUiState?.signUpError ?: "unknown error",
                color = Color.Red,
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.userNameSignUp ?: "",
            onValueChange = {loginViewModel?.onUserNameChangeSignUp(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Email")
            },
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.passwordSignUp ?: "",
            onValueChange = { loginViewModel?.onPasswordChangeSignUp(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.confirmpasswordSignUp ?: "",
            onValueChange = { loginViewModel?.onConfirmPasswordChange(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Confirm Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )

        Button(onClick = { loginViewModel?.createUser(context) }) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Already have an Account?")
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = { onNavToLoginPage.invoke() }) {
                Text(text = "Sign In")
            }

        }

        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                onNavToMainScreen.invoke()
            }
        }






    }


}

// Preview für Login Screen
@Preview(showSystemUi = true)
@Composable
fun PrevLoginPageScreen() {
    NavigationDrawerTheme() {
        LoginPageScreen( onNavToMainScreen = { /*TODO*/ }) {

        }
    }
}

// Preview für SignUp Screen
@Preview(showSystemUi = true)
@Composable
fun SignUpPageScreen() {
    NavigationDrawerTheme() {
        SignUpPageScreen( onNavToMainScreen = { /*TODO*/ }) {

        }
    }
}



/*
@Composable
fun LoginHomeScreen() {
    LoginPageScreen()


@Composable
fun LandingPageScreen() {

    Surface(color = MaterialTheme.colors.primaryVariant) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val path = Path()
                    val x = size.width
                    val y = size.height
                    val center = size.center
                    path.apply {
                        moveTo(0f, 0f)
                        lineTo(x, 0f)
                        cubicTo(
                            x1 = x,
                            y1 = center.y / 2,
                            x2 = x,
                            y2 = center.y,
                            x3 = 0f,
                            y3 = center.y

                        )

                    }

                    drawPath(path = path, color = Color.LightGray)

                }
        ) {

            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Hello",
                    style = MaterialTheme.typography.h1,
                    fontSize = 30.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Welcome to CampusNav ",
                    style = MaterialTheme.typography.body1,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.campusnav_icon),
                    contentDescription = null,
                    modifier = Modifier.size(34.dp)
                )


            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .wrapContentSize(align = Alignment.BottomCenter)) {

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "Sign In")
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.onSurface
                    )
                ) {
                    Text(text = "Sign up")
                }

            }

        }
    }


}


@Composable
fun LoginPageScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToMainScreen: () -> Unit,
    onNavToSignUpPage: () -> Unit,
) {
    // create an instance of loginUi
    val loginUiState = loginViewModel?.loginUiState

    // to check if there is an error
    val isError = loginUiState?.loginError != null

    val context = LocalContext.current


    Surface(
        color = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        val (username, onUserNameChange) = remember {
            mutableStateOf("")
        }
        val (password, onPasswordChange) = remember {
            mutableStateOf("")
        }
        val (checked, onCheckedChange) = remember {
            mutableStateOf(false)
        }
        Column {

            Text(
                text = "Login",
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(16.dp),
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = onUserNameChange,
                label = { Text(text = "UserName") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(text = "Password") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                shape = MaterialTheme.shapes.medium
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(text = "Remember me")
                }

                TextButton(
                    onClick = { /*TODO*/ },
                ) {
                    Text(text = "Forgot Password")
                }

            }

            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Login")
            }
        }




        Row(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomCenter)
        ) {
            Text(text = "Don't have an Account?")
            TextButton(
                onClick = { /*TODO*/ },
            ) {
                Text(text = "Sign Up")
            }


        }


    }


}


@Composable
fun SignUpPageScreen() {
    val signUpState = SignUpState()
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp),
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = signUpState.firstName,
                    onValueChange = {
                        signUpState.firstNameChenged(newValue = it)
                        Log.i("SignUp", "SignUpPageScreen: $it")
                    },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    label = { Text(text = "First Name") }
                )
                Log.i("SignUp", "SignUpPageScreen: ${signUpState.firstName}")
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = signUpState.lastName,
                    onValueChange = { signUpState.lastNameChange(it) },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    label = { Text(text = "Last Name") }
                )

            }
            OutlinedTextField(
                value = signUpState.emailAddress,
                onValueChange = { signUpState.emailAddressChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                label = { Text(text = "Email Address") }
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = signUpState.password,
                onValueChange = { signUpState.password(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = signUpState.confirmPassword,
                onValueChange = { signUpState.confirmPasswordChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium,
                label = { Text(text = "Confirm Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Checkbox(
                    checked = signUpState.checked,
                    onCheckedChange = { signUpState.checkedChange(it) }
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "Agree with privacy policy")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Sign Up")

            }



            Row(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentSize(align = Alignment.BottomCenter)) {

                Text(text = "Already have an account?")
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Sign In",
                    modifier = Modifier.clickable {

                    },
                    color = MaterialTheme.colors.primary
                )


            }


        }


    }


}


@Preview(showBackground = true)
@Composable
fun LandingPagePrev() {
    NavigationDrawerTheme() {
        LandingPageScreen()
    }
}





 */









