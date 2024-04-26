package ru.mirea.dentalclinic.data.repositories

import ru.mirea.dentalclinic.data.mappers.DoctorMapper
import ru.mirea.dentalclinic.data.models.DoctorsResponse
import ru.mirea.dentalclinic.data.services.DoctorService
import ru.mirea.dentalclinic.domain.models.Doctor
import ru.mirea.dentalclinic.domain.models.DoctorPage
import ru.mirea.dentalclinic.domain.models.DoctorWithProcedures
import ru.mirea.dentalclinic.domain.repositories.DoctorRepository
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val doctorService: DoctorService,
    private val doctorMapper: DoctorMapper
) : DoctorRepository {
    override suspend fun getSortedByRateDoctors(page: Int): Result<DoctorPage> {
        return runCatching {
            val response: DoctorsResponse = doctorService.getBestDoctors()
            DoctorPage(
                doctors = response.doctors.map(doctorMapper::mapToDoctor),
                page = page
            )
        }
    }

    override suspend fun searchDoctors(query: String, page: Int): Result<DoctorPage> {
        return runCatching {
            val response = doctorService.findDoctorsByName(page, query)
            DoctorPage(
                doctors = response.doctors.map(doctorMapper::mapToDoctor),
                page = page
            )
        }
    }

    override suspend fun getDoctorById(doctorId: Long): Result<Doctor> {
        return runCatching {
            val response = doctorService.getDoctorById(doctorId)
            doctorMapper.mapToDoctor(response)
        }
    }
}
