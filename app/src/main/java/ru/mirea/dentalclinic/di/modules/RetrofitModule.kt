package ru.mirea.dentalclinic.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mirea.dentalclinic.data.Constants
import javax.inject.Qualifier

@Qualifier
annotation class MainBackend

@Module
class RetrofitModule {
    @Provides
    @MainBackend
    fun provideRetrofit(): Retrofit {
        val gson = GsonConverterFactory.create()
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(gson).build()
    }
}