package ru.mirea.dentalclinic.presentation.doctorpage

import kotlinx.coroutines.flow.StateFlow

class DoctorPagePresenterImpl(
    private val viewModel: DoctorPageViewModel
) : DoctorPagePresenter {
    override val state: StateFlow<DoctorPageState>
        get() = viewModel.state

    override fun update() {
        viewModel.update()
    }
}