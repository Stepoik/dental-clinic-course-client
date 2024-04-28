package ru.mirea.dentalclinic.presentation.homescreen.formatters

import ru.mirea.dentalclinic.domain.models.Appointment
import ru.mirea.dentalclinic.presentation.common.formatters.DoctorFormatter
import ru.mirea.dentalclinic.presentation.homescreen.models.HomeAppointmentVO
import ru.mirea.dentalclinic.utils.asTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeAppointmentFormatter @Inject constructor(
    private val doctorFormatter: DoctorFormatter
) {
    private val timePattern = "HH:mm"
    private val timeFormatter = DateTimeFormatter.ofPattern(timePattern)

    fun format(appointment: Appointment): HomeAppointmentVO {
        val startTime = appointment.start.asTime()
        val endTime = appointment.end.asTime()
        val time = "${timeFormatter.format(startTime)} - ${timeFormatter.format(endTime)}"

        val doctorVo = doctorFormatter.format(appointment.doctor)
        return HomeAppointmentVO(
            time = time,
            doctorName = doctorVo.name
        )
    }
}