package ru.mirea.dentalclinic.presentation.authentication.registration

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.mirea.dentalclinic.utils.ComponentViewModel
import ru.mirea.dentalclinic.utils.appComponent
import ru.mirea.dentalclinic.utils.daggerViewModel

const val REGISTRATION_ROUTE = "registration"

fun NavGraphBuilder.registration(onLogged: () -> Unit) {
    composable(REGISTRATION_ROUTE) {
        val appComponent = appComponent()
        val componentViewModel = daggerViewModel {
            ComponentViewModel(appComponent.registrationScreenComponent())
        }
        val registrationViewModel = daggerViewModel {
            componentViewModel.component.registrationViewModel()
        }
        val presenter = RegistrationPresenterImpl(
            viewModel = registrationViewModel,
            onLogged = onLogged
        )
        RegistrationScreen(presenter = presenter)
    }
}

fun NavController.navigateToRegistration() {
    navigate(REGISTRATION_ROUTE)
}