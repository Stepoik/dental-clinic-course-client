package ru.mirea.dentalclinic.presentation.appointment

import kotlinx.coroutines.flow.StateFlow

interface AppointmentPresenter {
    val selectedDay: StateFlow<String>
    val state: StateFlow<AppointmentScreenState>

    fun pickPreviousDay() {}

    fun pickNextDay() {}
}