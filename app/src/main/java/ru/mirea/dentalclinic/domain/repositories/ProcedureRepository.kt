package ru.mirea.dentalclinic.domain.repositories

import ru.mirea.dentalclinic.domain.models.Procedure
import ru.mirea.dentalclinic.domain.models.ProcedureCount

interface ProcedureRepository {
    suspend fun getProceduresWithCount(): Result<List<ProcedureCount>>

    suspend fun getProceduresByDoctorId(doctorId: Long): Result<List<Procedure>>
}