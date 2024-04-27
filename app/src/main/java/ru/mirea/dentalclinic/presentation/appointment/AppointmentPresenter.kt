package ru.mirea.dentalclinic.presentation.appointment

import kotlinx.coroutines.flow.StateFlow

interface AppointmentPresenter {
    val selectedDay: StateFlow<String>
    val headerState: StateFlow<AppointmentScreenHeaderState>
    val state: StateFlow<AppointmentScreenState>

    fun pickPreviousDay() {}

    fun pickNextDay() {}
}