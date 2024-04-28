package ru.mirea.dentalclinic.presentation.splash

import kotlinx.coroutines.flow.StateFlow

interface SplashScreenPresenter {
    val state: StateFlow<SplashScreenState>

    fun navigateToHome()

    fun navigateToAuth()
}