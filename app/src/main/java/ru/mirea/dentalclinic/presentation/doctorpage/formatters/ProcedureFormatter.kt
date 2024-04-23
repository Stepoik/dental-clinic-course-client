package ru.mirea.dentalclinic.presentation.doctorpage.formatters

import ru.mirea.dentalclinic.domain.models.Procedure
import ru.mirea.dentalclinic.presentation.doctorpage.models.ProcedureVO
import javax.inject.Inject

class ProcedureFormatter @Inject constructor() {
    fun format(procedure: Procedure): ProcedureVO {
        return ProcedureVO(name = procedure.name)
    }
}