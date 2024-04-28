package ru.mirea.dentalclinic.domain.repositories

import kotlinx.coroutines.flow.StateFlow

interface AuthRemoteRepository {

    suspend fun login(username: String, password: String): Result<Unit>

    suspend fun logout()

    suspend fun register(username: String, password: String): Result<Unit>
}