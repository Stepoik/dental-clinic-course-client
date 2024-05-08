package ru.mirea.dentalclinic.di.features

import android.content.Context
import dagger.Component
import ru.mirea.dentalclinic.domain.usecases.GetProceduresUseCase
import ru.mirea.dentalclinic.presentation.procedures.ProceduresViewModel

interface ProceduresDependencies {
    val getProceduresUseCase: GetProceduresUseCase
    val context: Context
}

@Component(dependencies = [ProceduresDependencies::class])
interface ProceduresComponent {
    @Component.Factory
    interface Factory {
        fun create(procedureDependencies: ProceduresDependencies): ProceduresComponent
    }

    fun proceduresViewModel(): ProceduresViewModel
}