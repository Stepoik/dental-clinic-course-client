package ru.mirea.dentalclinic.data.repositories

import ru.mirea.dentalclinic.data.mappers.PatientMapper
import ru.mirea.dentalclinic.data.services.PatientService
import ru.mirea.dentalclinic.domain.models.Patient
import ru.mirea.dentalclinic.domain.repositories.PatientRepository
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val patientService: PatientService,
    private val patientMapper: PatientMapper
) : PatientRepository {
    override suspend fun getPatient(): Result<Patient> {
        return runCatching {
            patientMapper.mapToDomain(patientService.getPatient())
        }
    }
}