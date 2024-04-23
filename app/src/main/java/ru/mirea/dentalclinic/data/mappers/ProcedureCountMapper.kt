package ru.mirea.dentalclinic.data.mappers

import ru.mirea.dentalclinic.data.models.ProcedureCountDto
import ru.mirea.dentalclinic.domain.models.Procedure
import ru.mirea.dentalclinic.domain.models.ProcedureCount
import javax.inject.Inject

class ProcedureCountMapper @Inject constructor() {
    fun mapToDomain(procedureCountDto: ProcedureCountDto): ProcedureCount {
        return ProcedureCount(
            procedure = Procedure(procedureCountDto.name),
            count = procedureCountDto.count
        )
    }
}