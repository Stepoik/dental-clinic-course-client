package ru.mirea.dentalclinic.presentation.procedures

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appComponent
import ru.mirea.dentalclinic.utils.daggerViewModel

const val PROCEDURES_ROUTE = "procedures"

fun NavGraphBuilder.procedures(navController: NavController) {
    composable(PROCEDURES_ROUTE) {
        val appComponent = appComponent()
        val componentViewModel = daggerViewModel {
            ComponentViewModel(appComponent.proceduresComponent())
        }
        val viewModel = daggerViewModel {
            componentViewModel.component.proceduresViewModel()
        }
        val presenter = ProceduresPresenterImpl(
            viewModel = viewModel,
            navController = navController
        )
        ProceduresScreen(presenter = presenter)
    }
}

fun NavController.navigateToProcedures() {
    navigate(PROCEDURES_ROUTE)
}