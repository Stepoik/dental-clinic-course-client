package ru.mirea.dentalclinic.data.services

import retrofit2.http.Body
import retrofit2.http.POST
import ru.mirea.dentalclinic.data.models.JwtAuthResponse
import ru.mirea.dentalclinic.data.models.RegistrationRequest
import ru.mirea.dentalclinic.data.models.UserDetailsRequest

interface AuthService {
    @POST("auth/sign-up")
    suspend fun register(
        @Body userDetails: RegistrationRequest
    ): JwtAuthResponse

    @POST("auth/sign-in")
    suspend fun login(
        @Body userDetails: UserDetailsRequest
    ): JwtAuthResponse
}