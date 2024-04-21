package ru.mirea.dentalclinic.domain.repositories

import ru.mirea.dentalclinic.domain.models.ProcedureCount

interface ProcedureRepository {
    suspend fun getProceduresWithCount(): Result<List<ProcedureCount>>
}