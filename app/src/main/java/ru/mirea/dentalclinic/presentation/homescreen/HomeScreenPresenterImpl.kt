package ru.mirea.dentalclinic.presentation.homescreen

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.appointment.view.navigateToAppointmentScreen
import ru.mirea.dentalclinic.presentation.authentication.navigateToAuthentication
import ru.mirea.dentalclinic.presentation.doctorsearch.view.navigateToDoctorList
import ru.mirea.dentalclinic.presentation.doctorpage.view.navigateToDoctorPage
import ru.mirea.dentalclinic.presentation.proceduredoctors.navigateToProcedureDoctors
import ru.mirea.dentalclinic.presentation.procedures.navigateToProcedures

class HomeScreenPresenterImpl(
    private val viewModel: HomeViewModel,
    private val navController: NavController
) : HomeScreenPresenter {
    override val state: StateFlow<HomeScreenState> = viewModel.state

    override fun update() {
        viewModel.update()
    }

    override fun onErrorShowed() {
        viewModel.onErrorShowed()
    }

    override fun logout() {
        viewModel.logout()
    }

    override fun navigateToAuth() {
        navController.navigateToAuthentication()
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

    override fun navigateToProcedures() {
        navController.navigateToProcedures()
    }

    override fun navigateToProcedureDoctors(procedureName: String) {
        navController.navigateToProcedureDoctors(procedureName)
    }
}