package ru.mirea.dentalclinic.data.repositories

import ru.mirea.dentalclinic.data.mappers.AppointmentDtoMapper
import ru.mirea.dentalclinic.data.services.AppointmentService
import ru.mirea.dentalclinic.domain.models.Appointment
import ru.mirea.dentalclinic.domain.repositories.AppointmentRepository
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val appointmentService: AppointmentService,
    private val appointmentDtoMapper: AppointmentDtoMapper
) : AppointmentRepository {
    private val dateFormatPattern = "yyyy-MM-dd"
    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern(dateFormatPattern).withZone(ZoneId.systemDefault())

    override suspend fun getAppointmentByDateAndDoctorId(
        date: Date,
        doctorId: Long
    ): Result<List<Appointment>> {
        return runCatching {
            val appointments = appointmentService.getRecordByDateAndDoctorId(
                formatter.format(date.toInstant()),
                doctorId
            );
            appointments.appointments.map(appointmentDtoMapper::mapToDomain)
        }
    }

    override suspend fun bookAppointment(appointmentId: Long): Result<Nothing> {
        // TODO: Сделать
        return Result.failure(Exception())
    }
}