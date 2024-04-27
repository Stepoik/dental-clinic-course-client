package ru.mirea.dentalclinic.domain.usecases

import ru.mirea.dentalclinic.domain.models.Appointment
import ru.mirea.dentalclinic.domain.repositories.AppointmentRepository
import javax.inject.Inject

class BookAppointmentUseCase @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {
    suspend fun execute(appointmentId: Long): Result<Nothing> {
        return appointmentRepository.bookAppointment(appointmentId)
    }
}