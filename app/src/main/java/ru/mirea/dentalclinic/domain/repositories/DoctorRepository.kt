package ru.mirea.dentalclinic.domain.repositories

import ru.mirea.dentalclinic.domain.models.Doctor
import ru.mirea.dentalclinic.domain.models.DoctorPage
import ru.mirea.dentalclinic.domain.models.DoctorWithProcedures

interface DoctorRepository {
    suspend fun getSortedByRateDoctors(page: Int): Result<DoctorPage>

    suspend fun searchDoctors(query: String, page: Int): Result<DoctorPage>

    suspend fun getDoctorById(doctorId: Long): Result<Doctor>
}