package ru.mirea.dentalclinic.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.mirea.dentalclinic.data.Constants
import ru.mirea.dentalclinic.domain.repositories.AuthLocalRepository
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authLocalRepository: AuthLocalRepository
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking(context = Dispatchers.IO) {
            val authToken = authLocalRepository.getAuthToken()
            val requestBuilder: Request.Builder = chain.request()
                .newBuilder()
            if (authToken != null) {
                requestBuilder.addHeader(Constants.AUTH_HEADER, "Bearer $authToken")
            }
            val response = chain.proceed(requestBuilder.build())
            if (response.code == ResponseCode.FORBIDDEN) {
                authLocalRepository.logout()
            }
            response
        }
    }
}