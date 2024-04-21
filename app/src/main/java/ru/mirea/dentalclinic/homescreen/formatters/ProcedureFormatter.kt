package ru.mirea.dentalclinic.homescreen.formatters

import android.content.Context
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.domain.models.ProcedureCount
import ru.mirea.dentalclinic.homescreen.models.ProcedureVO
import javax.inject.Inject

class ProcedureFormatter @Inject constructor(
    private val context: Context
) {
    fun format(procedureCount: ProcedureCount): ProcedureVO {
        val doctorCount = context.resources.getQuantityString(R.plurals.doctor, procedureCount.count, procedureCount.count)
        return ProcedureVO(
            name = procedureCount.name,
            doctorCount = doctorCount
        )
    }
}