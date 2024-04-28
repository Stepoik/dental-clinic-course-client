package ru.mirea.dentalclinic.presentation.authentication.login

import kotlinx.coroutines.flow.StateFlow

interface LoginScreenPresenter {
    val state: StateFlow<LoginScreenState>

    fun onErrorShowed() {}

    fun onLoggedIn() {}

    fun navigateToRegistration() {}

    fun authenticate(username: String, password: String) {}
}