package ru.mirea.dentalclinic.di.features

import dagger.Component
import ru.mirea.dentalclinic.domain.repositories.AuthRemoteRepository

interface RegistrationDependencies {
    val authRemoteRepository: AuthRemoteRepository
}

@Component(dependencies = [RegistrationDependencies::class])
interface RegistrationScreenComponent {
    @Component.Factory
    interface Factory {
        fun create(registrationDependencies: RegistrationDependencies): RegistrationScreenComponent
    }
}