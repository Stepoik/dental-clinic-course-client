package ru.mirea.dentalclinic.domain.usecases

import ru.mirea.dentalclinic.domain.models.ProcedureCount
import ru.mirea.dentalclinic.domain.repositories.ProcedureRepository
import javax.inject.Inject

class GetProceduresUseCase @Inject constructor(
    private val procedureRepository: ProcedureRepository
) {
    suspend fun execute(): Result<List<ProcedureCount>> {
        return procedureRepository.getProceduresWithCount()
    }
}