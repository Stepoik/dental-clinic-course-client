package ru.mirea.dentalclinic.presentation.homescreen

import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.presentation.homescreen.models.PatientVO
import ru.mirea.dentalclinic.presentation.homescreen.models.ProcedureVO

data class HomeScreenState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val patient: PatientVO? = null,
    val procedures: List<ProcedureVO> = listOf(),
    val bestDoctors: List<DoctorVO> = listOf()
)
