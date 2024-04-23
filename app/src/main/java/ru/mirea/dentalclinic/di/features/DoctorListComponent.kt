package ru.mirea.dentalclinic.di.features

import android.content.Context
import dagger.Component
import dagger.Component.Factory
import ru.mirea.dentalclinic.domain.usecases.SearchDoctorsUseCase
import ru.mirea.dentalclinic.presentation.doctorlist.DoctorListViewModel

interface DoctorListDependencies {
    val searchDoctorsUseCase: SearchDoctorsUseCase
    val context: Context
}

@Component(dependencies = [DoctorListDependencies::class])
interface DoctorListComponent {
    @Component.Factory
    interface Factory {
        fun create(dependencies: DoctorListDependencies): DoctorListComponent
    }

    fun doctorListViewModel(): DoctorListViewModel
}