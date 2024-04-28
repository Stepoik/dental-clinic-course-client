package ru.mirea.dentalclinic.domain.usecases

import ru.mirea.dentalclinic.domain.repositories.AuthLocalRepository
import javax.inject.Inject

class IsLoginUseCase @Inject constructor(
    private val authRepository: AuthLocalRepository
) {
    suspend fun execute(): Boolean {
        return authRepository.getAuthToken() != null
    }
}