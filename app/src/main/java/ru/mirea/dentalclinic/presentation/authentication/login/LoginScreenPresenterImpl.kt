package ru.mirea.dentalclinic.presentation.authentication.login

import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.authentication.registration.navigateToRegistration

class LoginScreenPresenterImpl(
    private val viewModel: LoginViewModel,
    private val navController: NavController,
    private val onLoggedIn: () -> Unit
) : LoginScreenPresenter {
    override val state: StateFlow<LoginScreenState> = viewModel.state

    override fun onErrorShowed() {
        viewModel.onErrorShowed()
    }

    override fun onLoggedIn() {
        onLoggedIn.invoke()
    }

    override fun navigateToRegistration() {
        navController.navigateToRegistration()
    }

    override fun authenticate(username: String, password: String) {
        viewModel.authenticate(username = username, password = password)
    }
}