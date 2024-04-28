package ru.mirea.dentalclinic.presentation.splash.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mirea.dentalclinic.presentation.splash.SplashScreenPresenterImpl
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appComponent
import ru.mirea.dentalclinic.utils.daggerViewModel

const val SPLASH_SCREEN_ROUTE = "splash"

fun NavGraphBuilder.splash(navController: NavController) {
    composable(SPLASH_SCREEN_ROUTE) {
        val appComponent = appComponent()
        val componentViewModel = daggerViewModel {
            ComponentViewModel(appComponent.splashScreenComponent())
        }
        val splashViewModel = daggerViewModel {
            componentViewModel.component.splashViewModel()
        }
        val presenter = SplashScreenPresenterImpl(
            viewModel = splashViewModel,
            navController = navController
        )
        SplashScreen(presenter)
    }
}