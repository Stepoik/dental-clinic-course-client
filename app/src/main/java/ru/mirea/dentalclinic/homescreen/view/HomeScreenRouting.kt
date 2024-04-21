package ru.mirea.dentalclinic.homescreen.view

import androidx.compose.foundation.rememberScrollState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mirea.dentalclinic.di.features.homescreen.HomeScreenComponent
import ru.mirea.dentalclinic.homescreen.HomeScreenPresenterImpl
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appScope
import ru.mirea.dentalclinic.utils.daggerViewModel

const val HOME_SCREEN_ROUTE = "home_screen"

fun NavGraphBuilder.homeScreen() {
    composable(HOME_SCREEN_ROUTE) {
        val appScope = appScope()
        val componentViewModel: ComponentViewModel<HomeScreenComponent> = daggerViewModel {
            ComponentViewModel(appScope.homeScreenComponent())
        }
        val viewModel = daggerViewModel {
            componentViewModel.component.homeViewModel()
        }
        val presenter = HomeScreenPresenterImpl(viewModel)
        HomeScreen(presenter = presenter)
    }
}

fun NavController.navigateToHome() {
    navigate(HOME_SCREEN_ROUTE)
}