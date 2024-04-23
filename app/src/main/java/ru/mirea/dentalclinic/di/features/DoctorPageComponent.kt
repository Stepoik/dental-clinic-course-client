package ru.mirea.dentalclinic.di.features

import android.content.Context
import dagger.Component
import ru.mirea.dentalclinic.domain.usecases.GetDoctorByIdUseCase
import ru.mirea.dentalclinic.presentation.doctorpage.DoctorPageViewModel

interface DoctorPageDependencies {
    val getDoctorByIdUseCase: GetDoctorByIdUseCase
    val context: Context
}

@Component(dependencies = [DoctorPageDependencies::class])
interface DoctorPageComponent {
    @Component.Factory
    interface Factory {
        fun create(dependencies: DoctorPageDependencies): DoctorPageComponent
    }

    fun doctorPageViewModelFactory(): DoctorPageViewModel.Factory
}