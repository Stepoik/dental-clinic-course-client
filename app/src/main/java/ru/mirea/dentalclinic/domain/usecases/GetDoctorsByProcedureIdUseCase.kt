package ru.mirea.dentalclinic.domain.usecases

import ru.mirea.dentalclinic.domain.models.Doctor
import ru.mirea.dentalclinic.domain.repositories.DoctorRepository
import javax.inject.Inject

class GetDoctorsByProcedureNameUseCase @Inject constructor(
    private val doctorRepository: DoctorRepository
) {
    suspend fun execute(procedureName: String): Result<List<Doctor>> {
        return doctorRepository.getDoctorsByProcedure(procedureName)
    }
}