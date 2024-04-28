package ru.mirea.dentalclinic.presentation.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.repositories.AuthRemoteRepository
import javax.inject.Inject

private const val INCORRECT_DETAILS = "Неверные данные"

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRemoteRepository
) : ViewModel() {
    private val _state = MutableStateFlow<LoginScreenState>(LoginScreenState.Unauthorized())

    val state: StateFlow<LoginScreenState> get() = _state

    fun authenticate(username: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(
                username = username,
                password = password
            )
            result.onSuccess {
                _state.value = LoginScreenState.Authorized()
            }.onFailure {
                _state.value = LoginScreenState.Unauthorized(INCORRECT_DETAILS)
            }
        }
    }

    fun onErrorShowed() {
        _state.update {
            when (it) {
                is LoginScreenState.Unauthorized -> {
                    it.copy(errorMessage = null)
                }
                is LoginScreenState.Authorized -> {
                    it.copy(errorMessage = null)
                }
            }
        }
    }
}

sealed interface LoginScreenState {
    val errorMessage: String?

    data class Authorized(override val errorMessage: String? = null) : LoginScreenState
    data class Unauthorized(override val errorMessage: String? = null) : LoginScreenState
}