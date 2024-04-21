package ru.mirea.dentalclinic.domain.models

data class Doctor(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val image: String,
    val specialization: String,
    val rate: Float,
    val experience: Int
)
