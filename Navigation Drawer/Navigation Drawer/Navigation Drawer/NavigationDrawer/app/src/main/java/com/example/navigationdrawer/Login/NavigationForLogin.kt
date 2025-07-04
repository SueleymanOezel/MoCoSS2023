package com.example.navigationdrawer.Login



import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationdrawer.mvvm.MainScreen
import com.example.navigationdrawer.ui.theme.Screens.LoginScreen
import com.example.navigationdrawer.ui.theme.Screens.SignUpScreen


enum class LoginRoutes {
    Signup,
    SignIn
}

enum class HomeRoutes {
    Main,
    Detail
}


@Composable
fun NavToMain(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = LoginRoutes.SignIn.name
    ) {
        composable(route = LoginRoutes.SignIn.name) {
            LoginPageScreen(onNavToMainScreen = {
                navController.navigate(HomeRoutes.Main.name) {
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel

            ) {
                navController.navigate(LoginRoutes.Signup.name) {
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = LoginRoutes.Signup.name) {
            SignUpPageScreen(onNavToMainScreen = {
                navController.navigate(HomeRoutes.Main.name) {
                    popUpTo(LoginRoutes.Signup.name) {
                        inclusive = true
                    }
                }
            },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }

        }

        composable(route = HomeRoutes.Main.name) {
            MainScreen()
        }

    }


}