package ru.mirea.dentalclinic.data.mappers

import ru.mirea.dentalclinic.data.models.DoctorDto
import ru.mirea.dentalclinic.domain.models.Doctor
import javax.inject.Inject

class DoctorMapper @Inject constructor() {
    fun mapToDoctor(doctorDto: DoctorDto): Doctor {
        return Doctor(
            id = doctorDto.id ?: 0,
            firstName = doctorDto.firstName ?: "",
            lastName = doctorDto.lastName ?: "",
            middleName = doctorDto.middleName ?: "",
            image = doctorDto.image ?: "",
            rate = doctorDto.rate ?: 0f,
            experience = doctorDto.experience ?: 0,
            specialization = doctorDto.specialization?.specialization ?: ""
        )
    }
}