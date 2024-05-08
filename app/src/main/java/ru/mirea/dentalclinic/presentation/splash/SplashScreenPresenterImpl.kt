package ru.mirea.dentalclinic.presentation.splash

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.authentication.navigateToAuthentication
import ru.mirea.dentalclinic.presentation.homescreen.view.navigateToHome

class SplashScreenPresenterImpl(
    private val viewModel: SplashScreenViewModel,
    private val navController: NavController
): SplashScreenPresenter {
    override val state: StateFlow<SplashScreenState>
        get() = viewModel.state

    override fun navigateToHome() {
        navController.navigateToHome()
    }

    override fun navigateToAuth() {
        navController.navigateToAuthentication()
    }
}