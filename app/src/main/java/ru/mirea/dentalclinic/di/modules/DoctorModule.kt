package ru.mirea.dentalclinic.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mirea.dentalclinic.data.Constants
import ru.mirea.dentalclinic.data.repositories.DoctorRepositoryImpl
import ru.mirea.dentalclinic.data.services.DoctorService
import ru.mirea.dentalclinic.di.AppScope
import ru.mirea.dentalclinic.domain.repositories.DoctorRepository
import javax.inject.Qualifier

@Module
interface DoctorModule {
    @Binds
    @AppScope
    fun provideDoctorRepository(doctorRepositoryImpl: DoctorRepositoryImpl): DoctorRepository

    companion object {

        @AppScope
        @Provides
        fun provideDoctorService(@MainBackend retrofit: Retrofit): DoctorService {
            return retrofit.create(DoctorService::class.java)
        }
    }
}