package ru.mirea.dentalclinic.presentation.splash

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.authentication.AUTHENTICATION_ROUTE
import ru.mirea.dentalclinic.presentation.homescreen.view.HOME_SCREEN_ROUTE
import ru.mirea.dentalclinic.presentation.homescreen.view.navigateToHome

class SplashScreenPresenterImpl(
    private val viewModel: SplashScreenViewModel,
    private val navController: NavController
): SplashScreenPresenter {
    override val state: StateFlow<SplashScreenState>
        get() = viewModel.state

    override fun navigateToHome() {
        navController.navigate(HOME_SCREEN_ROUTE) {
            popUpTo(navController.currentDestination?.route ?: "") {
                inclusive = true
            }
        }
    }

    override fun navigateToAuth() {
        navController.navigate(AUTHENTICATION_ROUTE) {
            popUpTo(navController.currentDestination?.route ?: "") {
                inclusive = true
            }
        }
    }
}