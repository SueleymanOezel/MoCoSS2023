package com.example.navigationdrawer.Login

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class LoginViewModel (

    private val repo: Authentication = Authentication()
        ): ViewModel() {

        val currentUser = repo.currentUser

        val hasUser: Boolean
                get() = repo.hasUser()

        var loginUiState by mutableStateOf(LoginUiState())
                private set

        fun onUserNameChange( userName: String) {
                loginUiState = loginUiState.copy(userName = userName)
        }

        // here we can change the password
        fun onPasswordNameChange( password: String) {
                loginUiState = loginUiState.copy(password = password)
        }

        // here we can change the signup username
        fun onUserNameChangeSignUp( userName: String) {
                loginUiState = loginUiState.copy(userNameSignUp = userName)
        }

        // here we can chance the password
        fun onPasswordChangeSignUp( password: String) {
                loginUiState = loginUiState.copy(passwordSignUp = password)
        }

        // change their own confirmed password
        fun onConfirmPasswordChange( password: String) {
                loginUiState = loginUiState.copy(confirmpasswordSignUp = password)
        }

        // function which is going to help us to create a user
        // but first we want to validate these forms if they´re not empty because we don´t want to authenticate a user without any fields so create a prvate function
        private fun validateLoginForm() =
                loginUiState.userName.isNotBlank() &&
                        loginUiState.password.isNotBlank()

        private fun validateSignUpForm() =
                loginUiState.userName.isNotBlank() &&
                        loginUiState.passwordSignUp.isNotBlank() &&
                        loginUiState.confirmpasswordSignUp.isNotBlank()


        // function that will help us to create the user
        fun createUser(context: Context) = viewModelScope.launch {
                try {
                        if(!validateSignUpForm()) {
                                throw IllegalArgumentException(
                                        "E-Mail and Password can not be empty"
                                )
                        }
                        loginUiState = loginUiState.copy(isLoading = true)
                        if(loginUiState.passwordSignUp !=
                                loginUiState.confirmpasswordSignUp
                        ) {
                                throw IllegalArgumentException(
                                        "Password do not match"
                                )
                        }
                        loginUiState = loginUiState.copy(signUpError = null)
                        repo.createUser(
                                loginUiState.userNameSignUp,
                                loginUiState.passwordSignUp
                        ) {
                                isSuccessful ->
                                if(isSuccessful) {
                                        Toast.makeText(
                                                context,
                                                "success Login",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                        loginUiState = loginUiState.copy(isSuccessLogin = true)
                                } else {
                                         // Registration failed
                                        Toast.makeText(
                                                context,
                                                "Failed Login",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                        loginUiState = loginUiState.copy(isSuccessLogin = false)
                                }
                        }
                } catch (e: Exception) {
                        loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
                        e.printStackTrace()
                } finally {
                    loginUiState = loginUiState.copy(isLoading = false)
                }
        }


        fun loginUser(context: Context) = viewModelScope.launch {
                try {
                        if(!validateLoginForm()) {
                                throw IllegalArgumentException(
                                        "E-Mail and Password can not be empty"
                                )
                        }
                        loginUiState = loginUiState.copy(isLoading = true)
                        loginUiState = loginUiState.copy(loginError = null)
                        repo.logIn(
                            loginUiState.userName,
                            loginUiState.password
                        ) {
                                isSuccessful ->
                                if(isSuccessful) {
                                        Toast.makeText(
                                                context,
                                                "success Login",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                        loginUiState = loginUiState.copy(isSuccessLogin = true)
                                } else {
                                        Toast.makeText(
                                                context,
                                                "Failed Login",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                        loginUiState = loginUiState.copy(isSuccessLogin = false)
                                }
                        }
                } catch (e: Exception) {
                        loginUiState = loginUiState.copy(loginError = e.localizedMessage)
                        e.printStackTrace()
                } finally {
                        loginUiState = loginUiState.copy(isLoading = false)
                }
        }
        }

data class LoginUiState(

        val userName: String = "",
        val password: String = "",
        val userNameSignUp: String = "",
        val passwordSignUp: String = "",
        val confirmpasswordSignUp: String = "",
        val isLoading: Boolean = false,
        val isSuccessLogin: Boolean = false,
        val signUpError: String? = null,
        val loginError: String? = null
)