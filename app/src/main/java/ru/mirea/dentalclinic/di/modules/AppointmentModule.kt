package ru.mirea.dentalclinic.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.mirea.dentalclinic.data.repositories.AppointmentRepositoryImpl
import ru.mirea.dentalclinic.data.services.AppointmentService
import ru.mirea.dentalclinic.di.AppScope
import ru.mirea.dentalclinic.domain.repositories.AppointmentRepository

@Module
interface AppointmentModule {
    @Binds
    @AppScope
    fun provideAppointmentRepository(appointmentRepositoryImpl: AppointmentRepositoryImpl): AppointmentRepository

    companion object {
        @Provides
        @AppScope
        fun provideAppointmentService(
            @MainBackend retrofit: Retrofit
        ): AppointmentService {
            return retrofit.create(AppointmentService::class.java)
        }
    }
}