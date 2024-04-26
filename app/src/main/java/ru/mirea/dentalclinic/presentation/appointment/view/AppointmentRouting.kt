package ru.mirea.dentalclinic.presentation.appointment.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mirea.dentalclinic.presentation.appointment.AppointmentPresenterImpl
import ru.mirea.dentalclinic.presentation.appointment.AppointmentViewModel
import ru.mirea.dentalclinic.utils.daggerViewModel

const val APPOINTMENT_ROUTING = "appointment"

fun NavGraphBuilder.appointmentScreen() {
    composable(APPOINTMENT_ROUTING) {
        val viewModel = daggerViewModel {
            AppointmentViewModel()
        }
        val presenter = AppointmentPresenterImpl(viewModel)
        AppointmentScreen(presenter = presenter)
    }
}

fun NavController.navigateToAppointmentScreen() {
    navigate(APPOINTMENT_ROUTING)
}