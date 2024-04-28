package ru.mirea.dentalclinic.di.features

import dagger.Component
import ru.mirea.dentalclinic.domain.repositories.AuthRemoteRepository
import ru.mirea.dentalclinic.presentation.authentication.login.LoginViewModel

interface LoginScreenDependencies {
    val authRemoteRepository: AuthRemoteRepository
}

@Component(dependencies = [LoginScreenDependencies::class])
interface LoginScreenComponent {
    @Component.Factory
    interface Factory {
        fun create(loginScreenDependencies: LoginScreenDependencies): LoginScreenComponent
    }

    fun loginViewModel(): LoginViewModel
}