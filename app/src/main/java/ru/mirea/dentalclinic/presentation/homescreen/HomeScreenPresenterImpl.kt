package ru.mirea.dentalclinic.presentation.homescreen

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
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

    override fun navigateToDoctorList() {
        navController.navigateToDoctorList()
    }

    override fun navigateToDoctorPage(doctorId: Long) {
        navController.navigateToDoctorPage(doctorId)
    }
}