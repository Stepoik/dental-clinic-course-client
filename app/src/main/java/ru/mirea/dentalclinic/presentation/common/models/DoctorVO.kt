package ru.mirea.dentalclinic.presentation.common.models

data class DoctorVO(
    val id: Long,
    val image: String,
    val name: String,
    val specialization: String,
    val experience: String,
    val rate: String
)
