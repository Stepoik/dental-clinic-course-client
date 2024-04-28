package ru.mirea.dentalclinic.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.data.models.UserDetailsRequest
import ru.mirea.dentalclinic.data.services.AuthService
import ru.mirea.dentalclinic.data.utils.AuthScheme
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

    override suspend fun register(username: String, password: String): Result<Unit> {
        return runCatching {
            val authToken = authService.register(
                UserDetailsRequest(
                    username = username,
                    password = password
                )
            )
            authLocalRepository.login(authToken.token)
        }
    }
}