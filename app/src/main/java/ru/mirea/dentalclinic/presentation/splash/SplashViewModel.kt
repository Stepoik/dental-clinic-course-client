package ru.mirea.dentalclinic.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.usecases.IsLoginUseCase
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(
    private val isLoginUseCase: IsLoginUseCase
): ViewModel() {
    private val _state = MutableStateFlow<SplashScreenState>(SplashScreenState.Idle)
    val state: StateFlow<SplashScreenState> get() = _state

    init {
        viewModelScope.launch {
            if (isLoginUseCase.execute()) {
                _state.value = SplashScreenState.Authorized
            } else {
                _state.value = SplashScreenState.Unauthorized
            }
        }
    }
}

sealed class SplashScreenState() {
    object Idle: SplashScreenState()
    object Authorized: SplashScreenState()
    object Unauthorized: SplashScreenState()
}