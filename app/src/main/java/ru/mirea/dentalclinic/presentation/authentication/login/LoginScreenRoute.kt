package ru.mirea.dentalclinic.presentation.authentication.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appComponent
import ru.mirea.dentalclinic.utils.daggerViewModel

const val LOGIN_ROUTE = "login"

fun NavGraphBuilder.login(navController: NavController, onLoggedIn: () -> Unit) {
    composable(LOGIN_ROUTE) {
        val appComponent = appComponent()
        val componentViewModel = daggerViewModel {
            ComponentViewModel(appComponent.loginScreenComponent())
        }
        val viewModel = daggerViewModel {
            componentViewModel.component.loginViewModel()
        }
        val presenter = LoginScreenPresenterImpl(
            viewModel = viewModel,
            navController = navController,
            onLoggedIn = onLoggedIn
        )
        LoginScreen(presenter)
    }
}

fun NavController.navigateToLogin() {
    navigate(LOGIN_ROUTE)
}