package ru.mirea.dentalclinic.di.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mirea.dentalclinic.data.Constants
import ru.mirea.dentalclinic.data.utils.AuthInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Qualifier
annotation class MainBackend

@Module
class RetrofitModule {
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @MainBackend
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonConverterFactory.create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(gson)
            .build()
    }
}