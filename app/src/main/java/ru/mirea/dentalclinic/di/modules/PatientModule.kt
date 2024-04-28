package ru.mirea.dentalclinic.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.mirea.dentalclinic.data.repositories.PatientRepositoryImpl
import ru.mirea.dentalclinic.data.services.PatientService
import ru.mirea.dentalclinic.di.AppScope
import ru.mirea.dentalclinic.domain.repositories.PatientRepository

@Module
interface PatientModule {
    @Binds
    @AppScope
    fun providePatientRepository(patientRepositoryImpl: PatientRepositoryImpl): PatientRepository

    companion object {
        @Provides
        @AppScope
        fun providePatientService(@MainBackend retrofit: Retrofit): PatientService {
            return retrofit.create(PatientService::class.java)
        }
    }
}