package ru.mirea.dentalclinic.presentation.doctorsearch

import kotlinx.coroutines.flow.StateFlow

interface DoctorListPresenter {
    val state: StateFlow<DoctorListState>

    fun onQueryChanged(query: String)

    fun nextPage()

    fun navigateToDoctorPage(doctorId: Long)
}