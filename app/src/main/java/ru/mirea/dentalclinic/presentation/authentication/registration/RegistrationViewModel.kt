package ru.mirea.dentalclinic.presentation.authentication.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.repositories.AuthRemoteRepository
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val authRemoteRepository: AuthRemoteRepository
) : ViewModel() {
    private val _state = MutableStateFlow<RegistrationScreenState>(RegistrationScreenState.Unauthorized())

    val state: StateFlow<RegistrationScreenState> get() = _state

    fun authenticate(
        username: String,
        password: String,
        email: String,
        patientFirstName: String,
        patientLastName: String
    ) {
        viewModelScope.launch {
            val result = authRemoteRepository.register(
                username = username,
                password = password,
                email = email,
                firstName = patientFirstName,
                lastName = patientLastName
            )
            result.onSuccess {
                _state.value = RegistrationScreenState.Authorized()
            }.onFailure { exception ->
                _state.value = RegistrationScreenState.Unauthorized(errorMessage = exception.message)
            }
        }
    }

    fun onErrorShowed() {
        _state.update {
            when (it) {
                is RegistrationScreenState.Authorized -> {
                    it.copy(errorMessage = null)
                }
                is RegistrationScreenState.Unauthorized -> {
                    it.copy(errorMessage = null)
                }
            }
        }
    }
}

sealed interface RegistrationScreenState {
    val errorMessage: String?

    data class Authorized(override val errorMessage: String? = null) : RegistrationScreenState
    data class Unauthorized(override val errorMessage: String? = null) : RegistrationScreenState
}