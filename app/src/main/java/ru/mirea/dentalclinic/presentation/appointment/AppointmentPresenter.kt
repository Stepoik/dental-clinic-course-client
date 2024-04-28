package ru.mirea.dentalclinic.presentation.appointment

import kotlinx.coroutines.flow.StateFlow

interface AppointmentPresenter {
    val state: StateFlow<AppointmentScreenState>

    fun pickPreviousDay() {}

    fun pickNextDay() {}

    fun bookAppointment(appointmentId: Long) {}

    fun onErrorMessageShowed() {}
}