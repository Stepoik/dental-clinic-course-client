package ru.mirea.dentalclinic.domain.usecases

import ru.mirea.dentalclinic.domain.models.Appointment
import ru.mirea.dentalclinic.domain.repositories.AppointmentRepository
import java.util.Date
import javax.inject.Inject

class GetAppointmentsUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {
    suspend fun execute(date: Date, doctorId: Long): Result<List<Appointment>> {
        return appointmentRepository.getRecordByDateAndDoctorId(date, doctorId)
    }
}