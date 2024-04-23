package ru.mirea.dentalclinic.domain.usecases

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.mirea.dentalclinic.domain.models.DoctorWithProcedures
import ru.mirea.dentalclinic.domain.repositories.DoctorRepository
import ru.mirea.dentalclinic.domain.repositories.ProcedureRepository
import javax.inject.Inject

class GetDoctorByIdUseCase @Inject constructor(
    private val doctorRepository: DoctorRepository,
    private val procedureRepository: ProcedureRepository
) {
    suspend fun execute(doctorId: Long): Result<DoctorWithProcedures> = coroutineScope {
        val doctorJob = async { doctorRepository.getDoctorById(doctorId) }
        val proceduresJob = async { procedureRepository.getProceduresByDoctorId(doctorId) }

        val doctor = doctorJob.await().getOrElse { return@coroutineScope Result.failure(it) }
        val procedures = proceduresJob.await().getOrElse { return@coroutineScope Result.failure(it) }
        return@coroutineScope Result.success(
            DoctorWithProcedures(
                doctor = doctor,
                procedures = procedures
            )
        )
    }
}