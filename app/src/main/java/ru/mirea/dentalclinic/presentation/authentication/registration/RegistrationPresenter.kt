package ru.mirea.dentalclinic.presentation.authentication.registration

import kotlinx.coroutines.flow.StateFlow

interface RegistrationPresenter {
    val state: StateFlow<RegistrationScreenState>

    fun authenticate(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String
    ) {}

    fun onErrorShowed() {}

    fun onLogged() {}
}