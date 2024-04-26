package ru.mirea.dentalclinic.presentation.doctorpage

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.appointment.view.navigateToAppointmentScreen

class DoctorPagePresenterImpl(
    private val viewModel: DoctorPageViewModel,
    private val navController: NavController
) : DoctorPagePresenter {
    override val state: StateFlow<DoctorPageState>
        get() = viewModel.state

    override fun update() {
        viewModel.update()
    }

    override fun navigateToAppointment() {
        navController.navigateToAppointmentScreen(viewModel.doctorId)
    }
}