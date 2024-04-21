package ru.mirea.dentalclinic.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.mirea.dentalclinic.data.repositories.ProcedureRepositoryImpl
import ru.mirea.dentalclinic.data.services.ProcedureService
import ru.mirea.dentalclinic.di.AppScope
import ru.mirea.dentalclinic.domain.repositories.ProcedureRepository

@Module
interface ProcedureModule {
    @AppScope
    @Binds
    fun provideProcedureRepository(procedureRepositoryImpl: ProcedureRepositoryImpl): ProcedureRepository

    companion object {
        @AppScope
        @Provides
        fun provideProcedureService(@MainBackend retrofit: Retrofit): ProcedureService {
            return retrofit.create(ProcedureService::class.java)
        }
    }
}