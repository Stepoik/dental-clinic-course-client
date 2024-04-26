package ru.mirea.dentalclinic.presentation.doctorlist

import kotlinx.coroutines.flow.StateFlow
import retrofit2.http.Query

interface DoctorListPresenter {
    val state: StateFlow<DoctorListState>

    fun onQueryChanged(query: String)

    fun nextPage()

    fun navigateToDoctorPage(doctorId: Long)
}