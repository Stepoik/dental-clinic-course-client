package ru.mirea.dentalclinic.presentation.proceduredoctors

import kotlinx.coroutines.flow.StateFlow

interface ProcedureDoctorsPresenter {
    val state: StateFlow<ProcedureDoctorsState>

    fun update()

    fun navigateToDoctor(doctorId: Long)
}