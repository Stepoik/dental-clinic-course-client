package ru.mirea.dentalclinic.presentation.procedures

import kotlinx.coroutines.flow.StateFlow

interface ProceduresPresenter {
    val state: StateFlow<ProceduresState>

    fun navigateToDoctorsWithProcedure(procedureId: String)

    fun update()
}