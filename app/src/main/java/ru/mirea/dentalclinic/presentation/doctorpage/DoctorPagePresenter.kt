package ru.mirea.dentalclinic.presentation.doctorpage

import kotlinx.coroutines.flow.StateFlow

interface DoctorPagePresenter {
    val state: StateFlow<DoctorPageState>

    fun update()

    fun navigateToAppointment()
}