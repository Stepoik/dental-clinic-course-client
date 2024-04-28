package ru.mirea.dentalclinic.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.mirea.dentalclinic.data.Constants
import ru.mirea.dentalclinic.data.repositories.AuthLocalRepositoryImpl
import ru.mirea.dentalclinic.data.repositories.AuthRemoteRepositoryImpl
import ru.mirea.dentalclinic.data.services.AuthService
import ru.mirea.dentalclinic.di.AppScope
import ru.mirea.dentalclinic.domain.repositories.AuthLocalRepository
import ru.mirea.dentalclinic.domain.repositories.AuthRemoteRepository

@Module
interface AuthModule {
    @Binds
    @AppScope
    fun provideAuthRepository(authRepositoryImpl: AuthRemoteRepositoryImpl): AuthRemoteRepository

    @Binds
    @AppScope
    fun provideLocalAuthRepository(authLocalRepositoryImpl: AuthLocalRepositoryImpl): AuthLocalRepository

    companion object {
        @Provides
        @AppScope
        fun provideAuthDatastore(context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile(Constants.AUTH_DATASTORE_NAME)
            }
        }

        @Provides
        @AppScope
        fun provideAuthService(@MainBackend retrofit: Retrofit): AuthService {
            return retrofit.create(AuthService::class.java)
        }
    }
}