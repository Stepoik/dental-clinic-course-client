package ru.mirea.dentalclinic.presentation.homescreen.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mirea.dentalclinic.di.features.HomeScreenComponent
import ru.mirea.dentalclinic.presentation.homescreen.HomeScreenPresenterImpl
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appComponent
import ru.mirea.dentalclinic.utils.daggerViewModel

const val HOME_SCREEN_ROUTE = "home_screen"

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(HOME_SCREEN_ROUTE) {
        val appComponent = appComponent()
        val componentViewModel: ComponentViewModel<HomeScreenComponent> = daggerViewModel {
            ComponentViewModel(appComponent.homeScreenComponent())
        }
        val viewModel = daggerViewModel {
            componentViewModel.component.homeViewModel()
        }
        val presenter = HomeScreenPresenterImpl(viewModel, navController)
        HomeScreen(presenter = presenter)
    }
}

fun NavController.navigateToHome() {
    navigate(HOME_SCREEN_ROUTE)
}