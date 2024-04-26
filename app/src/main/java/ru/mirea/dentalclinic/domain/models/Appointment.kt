package ru.mirea.dentalclinic.domain.models

import java.util.Date

data class Appointment(
    val id: Long,
    val start: Int,
    val end: Int,
    val isBooked: Boolean,
    val day: Date
)
