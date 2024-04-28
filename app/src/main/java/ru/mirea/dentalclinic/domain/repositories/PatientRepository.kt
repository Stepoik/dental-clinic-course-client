package ru.mirea.dentalclinic.domain.repositories

import ru.mirea.dentalclinic.domain.models.Patient

interface PatientRepository {
    suspend fun getPatient(): Result<Patient>
}