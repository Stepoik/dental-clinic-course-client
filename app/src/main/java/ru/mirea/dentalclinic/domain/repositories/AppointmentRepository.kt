package ru.mirea.dentalclinic.domain.repositories

import ru.mirea.dentalclinic.domain.models.Appointment
import java.util.Date

interface AppointmentRepository {
    suspend fun getAppointmentByDateAndDoctorId(date: Date, doctorId: Long): Result<List<Appointment>>

    suspend fun bookAppointment(appointmentId: Long): Result<Nothing>
}