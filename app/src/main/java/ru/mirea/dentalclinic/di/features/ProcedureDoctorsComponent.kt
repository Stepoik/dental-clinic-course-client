package ru.mirea.dentalclinic.di.features

import android.content.Context
import dagger.Component
import ru.mirea.dentalclinic.domain.usecases.GetDoctorsByProcedureNameUseCase
import ru.mirea.dentalclinic.presentation.proceduredoctors.ProcedureDoctorsViewModel

interface ProcedureDoctorsDependencies {
    val getDoctorsByProcedureIdUseCase: GetDoctorsByProcedureNameUseCase
    val context: Context
}

@Component(dependencies = [ProcedureDoctorsDependencies::class])
interface ProcedureDoctorsComponent {
    @Component.Factory
    interface Factory {
        fun create(procedureDependencies: ProcedureDoctorsDependencies): ProcedureDoctorsComponent
    }

    fun procedureDoctorsViewModelFactory(): ProcedureDoctorsViewModel.Factory
}