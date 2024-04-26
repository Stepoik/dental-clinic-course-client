package ru.mirea.dentalclinic.presentation.appointment.models

data class AppointmentVO(
    val id: Long,
    val time: String,
    val isOpened: Boolean
)
