package ru.mirea.dentalclinic.presentation.appointment

import kotlinx.coroutines.flow.StateFlow

class AppointmentPresenterImpl(
    private val viewModel: AppointmentViewModel
) : AppointmentPresenter {
    override val state: StateFlow<AppointmentScreenState> = viewModel.state

    override fun pickPreviousDay() {
        viewModel.pickPreviousDay()
    }

    override fun pickNextDay() {
        viewModel.pickNextDay()
    }

    override fun bookAppointment(appointmentId: Long) {
        viewModel.bookAppointment(appointmentId)
    }

    override fun onErrorMessageShowed() {
        viewModel.onErrorMessageShowed()
    }
}