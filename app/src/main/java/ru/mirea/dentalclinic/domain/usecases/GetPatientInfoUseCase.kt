package ru.mirea.dentalclinic.domain.usecases

import ru.mirea.dentalclinic.domain.models.Patient
import ru.mirea.dentalclinic.domain.repositories.PatientRepository
import javax.inject.Inject

class GetPatientInfoUseCase @Inject constructor(
    private val patientRepository: PatientRepository
) {
    suspend fun execute(): Result<Patient> {
        return patientRepository.getPatient()
    }
}