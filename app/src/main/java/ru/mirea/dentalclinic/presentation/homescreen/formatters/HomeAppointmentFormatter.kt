package ru.mirea.dentalclinic.presentation.homescreen.formatters

import android.content.Context
import ru.mirea.dentalclinic.domain.models.Appointment
import ru.mirea.dentalclinic.presentation.common.formatters.DoctorFormatter
import ru.mirea.dentalclinic.presentation.homescreen.models.HomeAppointmentVO
import ru.mirea.dentalclinic.utils.asTime
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeAppointmentFormatter @Inject constructor(
    private val doctorFormatter: DoctorFormatter,
    private val context: Context
) {
    private val timePattern = "HH:mm"
    private val timeFormatter = DateTimeFormatter.ofPattern(timePattern)
    private val datePattern = "dd MMMM"
    private val dateFormatter = SimpleDateFormat(datePattern, context.resources.configuration.locales[0])

    fun format(appointment: Appointment): HomeAppointmentVO {
        val startTime = appointment.start.asTime()
        val endTime = appointment.end.asTime()
        val time = "${timeFormatter.format(startTime)} - ${timeFormatter.format(endTime)}"
        val date = dateFormatter.format(appointment.day)
        val doctorVo = doctorFormatter.format(appointment.doctor)
        return HomeAppointmentVO(
            time = time,
            doctorName = doctorVo.name,
            date = date
        )
    }
}