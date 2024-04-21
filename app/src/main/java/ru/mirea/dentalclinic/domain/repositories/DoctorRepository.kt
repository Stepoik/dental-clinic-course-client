package ru.mirea.dentalclinic.domain.repositories

import ru.mirea.dentalclinic.domain.models.Doctor

interface DoctorRepository {
    suspend fun getBestDoctors(): Result<List<Doctor>>
}