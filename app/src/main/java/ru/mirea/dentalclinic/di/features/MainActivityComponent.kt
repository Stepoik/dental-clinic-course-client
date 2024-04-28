package ru.mirea.dentalclinic.di.features

import dagger.Component
import ru.mirea.dentalclinic.MainViewModel
import ru.mirea.dentalclinic.domain.repositories.AuthLocalRepository
import ru.mirea.dentalclinic.domain.repositories.AuthRemoteRepository

interface MainActivityDependencies {
    val authRepository: AuthLocalRepository
}

@Component(dependencies = [MainActivityDependencies::class])
interface MainActivityComponent {
    @Component.Factory
    interface Factory {
        fun create(dependencies: MainActivityDependencies): MainActivityComponent
    }

    fun mainViewModel(): MainViewModel
}