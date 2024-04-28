package ru.mirea.dentalclinic.data.mappers

import ru.mirea.dentalclinic.data.models.PatientDto
import ru.mirea.dentalclinic.domain.models.Patient
import javax.inject.Inject

class PatientMapper @Inject constructor() {
    fun mapToDomain(patientDto: PatientDto): Patient {
        return Patient(
            id = patientDto.id,
            firstName = patientDto.firstName,
            lastName = patientDto.lastName
        )
    }
}