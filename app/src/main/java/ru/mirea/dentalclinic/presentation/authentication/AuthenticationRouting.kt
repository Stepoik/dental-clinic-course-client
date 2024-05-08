package ru.mirea.dentalclinic.presentation.authentication

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mirea.dentalclinic.presentation.authentication.login.LOGIN_ROUTE
import ru.mirea.dentalclinic.presentation.authentication.login.login
import ru.mirea.dentalclinic.presentation.authentication.registration.registration
import ru.mirea.dentalclinic.presentation.homescreen.view.navigateToHome

const val AUTHENTICATION_ROUTE = "authentication"

fun NavGraphBuilder.authentication(navController: NavController) {
    composable(AUTHENTICATION_ROUTE) {
        val localNavController = rememberNavController()
        NavHost(navController = localNavController, startDestination = LOGIN_ROUTE) {
            login(
                navController = localNavController,
                onLoggedIn = {
                    navController.navigateToHome()
                }
            )
            registration {
                navController.navigateToHome()
            }
        }
    }
}

fun NavController.navigateToAuthentication() {
    navigate(AUTHENTICATION_ROUTE) {
        popUpTo(currentDestination?.route ?: "") {
            inclusive = true
        }
    }
}