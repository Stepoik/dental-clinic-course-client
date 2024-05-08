package ru.mirea.dentalclinic.presentation.doctorsearch

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.doctorpage.view.navigateToDoctorPage

class DoctorListPresenterImpl(
    private val viewModel: DoctorListViewModel,
    private val navController: NavController
): DoctorListPresenter {
    override val state: StateFlow<DoctorListState>
        get() = viewModel.state

    override fun onQueryChanged(query: String) {
        viewModel.onQueryChanged(query)
    }

    override fun nextPage() {
        viewModel.onNextPage()
    }

    override fun navigateToDoctorPage(doctorId: Long) {
        navController.navigateToDoctorPage(doctorId)
    }
}