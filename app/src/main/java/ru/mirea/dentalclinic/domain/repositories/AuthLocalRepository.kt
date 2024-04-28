package ru.mirea.dentalclinic.domain.repositories

import kotlinx.coroutines.flow.StateFlow

interface AuthLocalRepository {
    val isAuthorized: StateFlow<Boolean>

    suspend fun getAuthToken(): String?

    suspend fun logout()

    suspend fun login(token: String)
}