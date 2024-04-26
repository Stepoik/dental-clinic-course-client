package ru.mirea.dentalclinic.presentation.appointment

import ru.mirea.dentalclinic.domain.models.Appointment
import ru.mirea.dentalclinic.presentation.appointment.models.AppointmentVO
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AppointmentVOMapper @Inject constructor() {
    private val timePattern = "HH:mm"
    private val timeFormatter = DateTimeFormatter.ofPattern(timePattern)

    fun map(appointment: Appointment): AppointmentVO {
        val startTime = appointment.start.asTime()
        val endTime = appointment.end.asTime()
        return AppointmentVO(
            id = appointment.id,
            time = "${timeFormatter.format(startTime)} - ${timeFormatter.format(endTime)}",
            isOpened = !appointment.isBooked
        )
    }

    private fun Int.asTime(): LocalTime {
        val startHour = this / 60
        val startMinute = this % 60
        return LocalTime.of(startHour, startMinute)
    }
}