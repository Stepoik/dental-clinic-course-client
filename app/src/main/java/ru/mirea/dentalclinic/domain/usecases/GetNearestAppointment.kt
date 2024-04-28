package ru.mirea.dentalclinic.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mirea.dentalclinic.domain.models.Appointment
import ru.mirea.dentalclinic.domain.repositories.AppointmentRepository
import javax.inject.Inject

class GetNearestAppointment @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) {
    suspend fun execute(): Result<Appointment?> {
        val appointments =
            appointmentRepository.getUserAppointments().getOrElse { return Result.failure(it) }
        println(appointments)
        return Result.success(withContext(Dispatchers.IO) {
            appointments.sortedWith { app1, app2 ->
                if (app1.day.before(app2.day)) {
                    return@sortedWith -1
                } else if (app1.day.after(app2.day)) {
                    return@sortedWith 1
                } else {
                    return@sortedWith app1.start.compareTo(app2.start)
                }
            }.firstOrNull()
        })
    }
}