package ru.mirea.dentalclinic.presentation.homescreen

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.appointment.view.navigateToAppointmentScreen
import ru.mirea.dentalclinic.presentation.doctorlist.view.navigateToDoctorList
import ru.mirea.dentalclinic.presentation.doctorpage.view.navigateToDoctorPage

class HomeScreenPresenterImpl(
    private val viewModel: HomeViewModel,
    private val navController: NavController
): HomeScreenPresenter {
    override val state: StateFlow<HomeScreenState> = viewModel.state

    override fun update() {
        viewModel.update()
    }

    override fun onErrorShowed() {
        viewModel.onErrorShowed()
    }

    override fun navigateToDoctorList() {
        navController.navigateToDoctorList()
    }

    override fun navigateToDoctorPage(doctorId: Long) {
        navController.navigateToDoctorPage(doctorId)
    }

    override fun navigateToAppointment(doctorId: Long) {
        navController.navigateToAppointmentScreen(doctorId)
    }

}