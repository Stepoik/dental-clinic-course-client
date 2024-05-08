package ru.mirea.dentalclinic.presentation.authentication.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.authentication.login.LoginScreenPresenter
import ru.mirea.dentalclinic.presentation.authentication.login.LoginScreenState
import ru.mirea.dentalclinic.presentation.common.components.DefaultTextField
import ru.mirea.dentalclinic.ui.theme.Blue80
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme
import ru.mirea.dentalclinic.ui.theme.White

const val ERROR_DELAY = 1000L

@Composable
fun RegistrationScreen(presenter: RegistrationPresenter) {
    val state = presenter.state.collectAsState().value
    var isError by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(state) {
        if (state is RegistrationScreenState.Authorized) {
            presenter.onLogged()
        } else if (state.errorMessage != null) {
            isError = true
            delay(ERROR_DELAY)
            presenter.onErrorShowed()
        } else {
            isError = false
        }
    }
    Scaffold { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            RegistrationForm(presenter = presenter, modifier = Modifier.fillMaxSize())
            AnimatedVisibility(
                visible = isError,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(text = stringResource(id = R.string.auth_error))
            }
        }
    }
}

@Composable
fun RegistrationForm(presenter: RegistrationPresenter, modifier: Modifier = Modifier) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.sign_up))
        DefaultTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = stringResource(id = R.string.username)) })
        DefaultTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(id = R.string.password)) },
            modifier = Modifier.padding(top = 20.dp)
        )
        DefaultTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(id = R.string.email)) },
            modifier = Modifier.padding(top = 20.dp)
        )
        DefaultTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = stringResource(id = R.string.firstName)) },
            modifier = Modifier.padding(top = 20.dp)
        )
        DefaultTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = stringResource(id = R.string.lastName)) },
            modifier = Modifier.padding(top = 20.dp)
        )
        Text(
            text = stringResource(id = R.string.sign_up),
            color = White,
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    presenter.authenticate(
                        username = username,
                        password = password,
                        email = email,
                        firstName = firstName,
                        lastName = lastName
                    )
                }
                .background(Blue80)
                .padding(10.dp)
        )
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    val presenter = object : RegistrationPresenter {
        override val state: StateFlow<RegistrationScreenState>
            get() = MutableStateFlow(RegistrationScreenState.Unauthorized())

    }
    DentalClinicTheme {
        RegistrationScreen(presenter = presenter)
    }
}