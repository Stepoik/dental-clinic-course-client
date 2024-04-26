package ru.mirea.dentalclinic.domain.repositories

import ru.mirea.dentalclinic.domain.models.Appointment
import java.util.Date

interface AppointmentRepository {
    suspend fun getRecordByDateAndDoctorId(date: Date, doctorId: Long): Result<List<Appointment>>
}