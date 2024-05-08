package ru.mirea.dentalclinic.data.repositories

import ru.mirea.dentalclinic.data.models.RegistrationRequest
import ru.mirea.dentalclinic.data.models.UserDetailsRequest
import ru.mirea.dentalclinic.data.services.AuthService
import ru.mirea.dentalclinic.domain.repositories.AuthLocalRepository
import ru.mirea.dentalclinic.domain.repositories.AuthRemoteRepository
import javax.inject.Inject

class AuthRemoteRepositoryImpl @Inject constructor(
    private val authLocalRepository: AuthLocalRepository,
    private val authService: AuthService
) : AuthRemoteRepository {

    override suspend fun login(username: String, password: String): Result<Unit> {
        return runCatching {
            val authToken = authService.login(
                UserDetailsRequest(
                    username = username,
                    password = password
                )
            )
            authLocalRepository.login(authToken.token)
        }
    }

    override suspend fun logout() {
        authLocalRepository.logout()
    }

    override suspend fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String
    ): Result<Unit> {
        return runCatching {
            val authToken = authService.register(
                RegistrationRequest(
                    username = username,
                    password = password,
                    email = email,
                    firstName = firstName,
                    lastName = lastName
                )
            )
            authLocalRepository.login(authToken.token)
        }
    }

}