package ru.mirea.dentalclinic.data.mappers

import ru.mirea.dentalclinic.data.models.AppointmentDto
import ru.mirea.dentalclinic.domain.models.Appointment
import javax.inject.Inject

class AppointmentDtoMapper @Inject constructor() {
    fun mapToDomain(recordDto: AppointmentDto): Appointment {
        return Appointment(
            id = recordDto.id,
            start = recordDto.start,
            end = recordDto.end,
            isBooked = recordDto.isBooked,
            day = recordDto.day
        )
    }
}