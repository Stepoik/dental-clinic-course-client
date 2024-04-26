package ru.mirea.dentalclinic.presentation.appointment.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.mirea.dentalclinic.presentation.appointment.AppointmentPresenterImpl
import ru.mirea.dentalclinic.presentation.appointment.AppointmentViewModel
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appComponent
import ru.mirea.dentalclinic.utils.daggerViewModel

private const val DOCTOR_ID = "doctorId"

const val APPOINTMENT_ROUTING = "appointment/{$DOCTOR_ID}"
private const val APPOINTMENT_PATTERN = "appointment/%d"

fun NavGraphBuilder.appointmentScreen(navController: NavController) {
    composable(
        APPOINTMENT_ROUTING,
        arguments = listOf(navArgument(DOCTOR_ID) { type = NavType.LongType })
    ) { backStackEntry ->
        val doctorId: Long = backStackEntry.arguments?.getLong(DOCTOR_ID) ?: navController.popBackStack().let {
            return@composable
        }
        val appComponent = appComponent()
        val componentViewModel = daggerViewModel {
            ComponentViewModel(appComponent.appointmentComponent())
        }
        val appointmentVMFactory = componentViewModel.component
        val viewModel = daggerViewModel {
            appointmentVMFactory.appointmentViewModelFactory().create(doctorId)
        }
        val presenter = AppointmentPresenterImpl(viewModel)
        AppointmentScreen(presenter = presenter)
    }
}

fun NavController.navigateToAppointmentScreen(doctorId: Long) {
    navigate(APPOINTMENT_PATTERN.format(doctorId))
}