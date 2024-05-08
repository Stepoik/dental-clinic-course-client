package ru.mirea.dentalclinic.presentation.authentication.registration

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegistrationPresenterImpl @Inject constructor(
    private val viewModel: RegistrationViewModel,
    private val onLogged: () -> Unit
) : RegistrationPresenter {
    override val state: StateFlow<RegistrationScreenState>
        get() = viewModel.state

    override fun authenticate(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String
    ) {
        viewModel.authenticate(
            username = username,
            password = password,
            email = email,
            patientFirstName = firstName,
            patientLastName = lastName
        )
    }

    override fun onErrorShowed() {
        viewModel.onErrorShowed()
    }

    override fun onLogged() {
        onLogged.invoke()
    }
}