package ru.mirea.dentalclinic.data.mappers

import ru.mirea.dentalclinic.data.models.ProcedureDto
import ru.mirea.dentalclinic.domain.models.Procedure
import javax.inject.Inject

class ProcedureDtoMapper @Inject constructor() {
    fun mapToDomain(procedureDto: ProcedureDto): Procedure {
        return Procedure(
            name = procedureDto.name
        )
    }
}