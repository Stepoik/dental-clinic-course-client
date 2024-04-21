package ru.mirea.dentalclinic.homescreen

import ru.mirea.dentalclinic.homescreen.models.DoctorVO
import ru.mirea.dentalclinic.homescreen.models.PatientVO
import ru.mirea.dentalclinic.homescreen.models.ProcedureVO

data class HomeScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val patient: PatientVO? = null,
    val procedures: List<ProcedureVO> = listOf(),
    val bestDoctors: List<DoctorVO> = listOf()
)
