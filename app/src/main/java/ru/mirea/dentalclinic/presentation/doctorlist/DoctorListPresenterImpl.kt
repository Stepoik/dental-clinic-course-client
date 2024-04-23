package ru.mirea.dentalclinic.presentation.doctorlist

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DoctorListPresenterImpl(
    private val viewModel: DoctorListViewModel
): DoctorListPresenter {
    override val state: StateFlow<DoctorListState>
        get() = viewModel.state

    override fun onQueryChanged(query: String) {
        viewModel.onQueryChanged(query)
    }

    override fun nextPage() {
        viewModel.onNextPage()
    }

}