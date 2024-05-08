package ru.mirea.dentalclinic.presentation.proceduredoctors

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.doctorpage.view.navigateToDoctorPage
import ru.mirea.dentalclinic.presentation.procedures.ProceduresPresenter

class ProcedureDoctorsPresenterImpl(
    private val viewModel: ProcedureDoctorsViewModel,
    private val navController: NavController
) : ProcedureDoctorsPresenter {
    override val state: StateFlow<ProcedureDoctorsState>
        get() = viewModel.state

    override fun update() {
        viewModel.update()
    }

    override fun navigateToDoctor(doctorId: Long) {
        navController.navigateToDoctorPage(doctorId)
    }
}