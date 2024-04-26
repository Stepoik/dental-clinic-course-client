package ru.mirea.dentalclinic.presentation.doctorpage.view

import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.mirea.dentalclinic.presentation.doctorpage.DoctorPagePresenter
import ru.mirea.dentalclinic.presentation.doctorpage.DoctorPagePresenterImpl
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appComponent
import ru.mirea.dentalclinic.utils.daggerViewModel

private const val DOCTOR_ID = "doctorId"

const val DOCTOR_PAGE_ROUTE = "doctor_page/{$DOCTOR_ID}"
private const val DOCTOR_PAGE_PATTERN = "doctor_page/%d"

fun NavGraphBuilder.doctorPage(navController: NavController) {
    composable(
        DOCTOR_PAGE_ROUTE,
        arguments = listOf(navArgument(DOCTOR_ID) { type = NavType.LongType })
    ) { backStackEntry ->
        val id: Long = backStackEntry.arguments?.getLong(DOCTOR_ID) ?: navController.popBackStack().let {
            return@composable
        }
        val appComponent = appComponent()
        val componentViewModel = daggerViewModel {
            ComponentViewModel(appComponent.doctorPageComponent())
        }
        val viewModel = daggerViewModel {
            val viewModelFactory = componentViewModel.component.doctorPageViewModelFactory()
            viewModelFactory.create(id)
        }
        val presenter = DoctorPagePresenterImpl(viewModel, navController)
        DoctorPageScreen(presenter = presenter)
    }
}

fun NavController.navigateToDoctorPage(doctorId: Long) {
    navigate(DOCTOR_PAGE_PATTERN.format(doctorId))
}