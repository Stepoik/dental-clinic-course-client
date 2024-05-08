package ru.mirea.dentalclinic.presentation.proceduredoctors

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appComponent
import ru.mirea.dentalclinic.utils.daggerViewModel

private const val PROCEDURE_ID = "procedureId"
const val PROCEDURE_DOCTORS_ROUTE = "procedures_doctor/{$PROCEDURE_ID}"
const val PROCEDURE_DOCTORS_ROUTE_PATTERN = "procedures_doctor/%s"

fun NavGraphBuilder.procedureDoctors(navController: NavController) {
    composable(
        PROCEDURE_DOCTORS_ROUTE,
        arguments = listOf(navArgument(PROCEDURE_ID) { type = NavType.StringType })
    ) { backStackEntry ->
        val procedureId: String =
            backStackEntry.arguments?.getString(PROCEDURE_ID) ?: navController.popBackStack().let {
                return@composable
            }
        val appComponent = appComponent()
        val componentViewModel = daggerViewModel {
            ComponentViewModel(appComponent.procedureDoctorComponent())
        }
        val viewModel = daggerViewModel {
            componentViewModel.component.procedureDoctorsViewModelFactory().create(procedureId)
        }
        val presenter = ProcedureDoctorsPresenterImpl(
            viewModel = viewModel,
            navController = navController
        )
        ProcedureDoctorsScreen(presenter = presenter)
    }
}

fun NavController.navigateToProcedureDoctors(procedureId: String) {
    navigate(PROCEDURE_DOCTORS_ROUTE_PATTERN.format(procedureId))
}