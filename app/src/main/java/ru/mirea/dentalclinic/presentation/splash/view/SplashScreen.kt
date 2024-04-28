package ru.mirea.dentalclinic.presentation.splash.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import ru.mirea.dentalclinic.presentation.common.components.Loading
import ru.mirea.dentalclinic.presentation.splash.SplashScreenPresenter
import ru.mirea.dentalclinic.presentation.splash.SplashScreenState

const val SPLASH_TIME = 1000L

@Composable
fun SplashScreen(presenter: SplashScreenPresenter) {
    var isLoaded by remember {
        mutableStateOf(false)
    }
    val state = presenter.state.collectAsState().value
    LaunchedEffect(true) {
        delay(SPLASH_TIME)
        isLoaded = true
    }
    LaunchedEffect(state, isLoaded) {
        if (isLoaded && state is SplashScreenState.Authorized) {
            presenter.navigateToHome()
        } else if (isLoaded && state is SplashScreenState.Unauthorized){
            presenter.navigateToAuth()
        }
    }
    Scaffold {
        Loading(modifier = Modifier.padding(it).fillMaxSize())
    }
}