package ru.mirea.dentalclinic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.repositories.AuthLocalRepository
import ru.mirea.dentalclinic.domain.repositories.AuthRemoteRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val authRepository: AuthLocalRepository
) : ViewModel() {
    private val _state = MutableStateFlow<MainActivityState>(MainActivityState.Unauthorized)
    val state: StateFlow<MainActivityState> get() = _state

    init {
        viewModelScope.launch {
            authRepository.isAuthorized.collect { isAuth ->
                if (isAuth) {
                    _state.value = MainActivityState.Authorized
                } else {
                    _state.value = MainActivityState.Unauthorized
                }
            }
        }
    }

    class Factory(
        private val create: () -> MainViewModel
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return create.invoke() as T
        }
    }
}

sealed class MainActivityState {
    object Authorized : MainActivityState()
    object Unauthorized : MainActivityState()
}