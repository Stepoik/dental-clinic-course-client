package ru.mirea.dentalclinic.domain.models

data class DoctorWithProcedures(
    val doctor: Doctor,
    val procedures: List<Procedure>
)
