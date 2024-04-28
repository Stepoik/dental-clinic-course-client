package ru.mirea.dentalclinic.presentation.homescreen

import kotlinx.coroutines.flow.StateFlow

interface HomeScreenPresenter {
    val state: StateFlow<HomeScreenState>
    fun update()

    fun onErrorShowed()

    fun logout()

    fun navigateToAuth()

    fun navigateToDoctorList()

    fun navigateToDoctorPage(doctorId: Long)

    fun navigateToAppointment(doctorId: Long)
}