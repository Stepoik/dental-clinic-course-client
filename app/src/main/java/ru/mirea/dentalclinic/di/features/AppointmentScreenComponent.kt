package ru.mirea.dentalclinic.di.features

import dagger.Component
import ru.mirea.dentalclinic.domain.usecases.GetAppointmentsUseCase
import ru.mirea.dentalclinic.presentation.appointment.AppointmentViewModel

interface AppointmentDependencies {
    val getAppointmentsUseCase: GetAppointmentsUseCase
}

@Component(dependencies = [AppointmentDependencies::class])
interface AppointmentScreenComponent {
    @Component.Factory
    interface Factory {
        fun create(appointmentDependencies: AppointmentDependencies): AppointmentScreenComponent
    }
    fun appointmentViewModelFactory(): AppointmentViewModel.Factory
}