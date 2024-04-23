package ru.mirea.dentalclinic.domain.models

data class DoctorPage(
    val doctors: List<Doctor>,
    val page: Int
)
