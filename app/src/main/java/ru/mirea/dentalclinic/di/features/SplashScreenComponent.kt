package ru.mirea.dentalclinic.di.features

import dagger.Component
import ru.mirea.dentalclinic.domain.repositories.AuthLocalRepository
import ru.mirea.dentalclinic.presentation.splash.SplashScreenViewModel

interface SplashScreenDependencies {
    val authLocalRepository: AuthLocalRepository
}

@Component(dependencies = [SplashScreenDependencies::class])
interface SplashScreenComponent {
    @Component.Factory
    interface Factory {
        fun create(splashScreenDependencies: SplashScreenDependencies): SplashScreenComponent
    }

    fun splashViewModel(): SplashScreenViewModel
}