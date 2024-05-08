package ru.mirea.dentalclinic.presentation.procedures

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.proceduredoctors.navigateToProcedureDoctors
import javax.inject.Inject

class ProceduresPresenterImpl @Inject constructor(
    private val viewModel: ProceduresViewModel,
    private val navController: NavController
): ProceduresPresenter {
    override val state: StateFlow<ProceduresState>
        get() = viewModel.state

    override fun navigateToDoctorsWithProcedure(procedureId: String) {
        navController.navigateToProcedureDoctors(procedureId)
    }

    override fun update() {
        viewModel.update()
    }
}