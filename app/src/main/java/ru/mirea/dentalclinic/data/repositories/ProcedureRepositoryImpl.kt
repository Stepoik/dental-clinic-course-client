package ru.mirea.dentalclinic.data.repositories

import ru.mirea.dentalclinic.data.mappers.ProcedureCountMapper
import ru.mirea.dentalclinic.data.services.ProcedureService
import ru.mirea.dentalclinic.domain.models.ProcedureCount
import ru.mirea.dentalclinic.domain.repositories.ProcedureRepository
import javax.inject.Inject

class ProcedureRepositoryImpl @Inject constructor(
    private val procedureService: ProcedureService,
    private val procedureCountMapper: ProcedureCountMapper
) : ProcedureRepository {
    override suspend fun getProceduresWithCount(): Result<List<ProcedureCount>> {
        return runCatching {
            val response = procedureService.getProcedures()
            response.procedures.map(procedureCountMapper::mapToDomain)
        }
    }
}