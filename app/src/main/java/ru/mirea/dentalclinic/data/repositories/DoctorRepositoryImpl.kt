package ru.mirea.dentalclinic.data.repositories

import ru.mirea.dentalclinic.data.mappers.DoctorMapper
import ru.mirea.dentalclinic.data.models.DoctorsResponse
import ru.mirea.dentalclinic.data.services.DoctorService
import ru.mirea.dentalclinic.domain.models.Doctor
import ru.mirea.dentalclinic.domain.repositories.DoctorRepository
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val doctorService: DoctorService,
    private val doctorMapper: DoctorMapper
): DoctorRepository {
    override suspend fun getBestDoctors(): Result<List<Doctor>> {
        return runCatching {
            val response: DoctorsResponse = doctorService.getBestDoctors()
            response.doctors.map(doctorMapper::mapToDoctor)
        }
    }
}