package ru.mirea.dentalclinic.presentation.homescreen

import kotlinx.coroutines.flow.StateFlow

interface HomeScreenPresenter {
    val state: StateFlow<HomeScreenState>
    fun update()

    fun navigateToDoctorList()

    fun navigateToDoctorPage(doctorId: Long)

    fun navigateToAppointment()
}