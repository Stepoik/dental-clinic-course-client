package ru.mirea.dentalclinic.presentation.authentication.login

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
import ru.mirea.dentalclinic.presentation.common.components.DefaultTextField
import ru.mirea.dentalclinic.ui.theme.Blue80
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme
import ru.mirea.dentalclinic.ui.theme.White

const val ERROR_DELAY = 1000L

@Composable
fun LoginScreen(presenter: LoginScreenPresenter) {
    val state = presenter.state.collectAsState().value
    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(state) {
        if (state is LoginScreenState.Authorized) {
            presenter.onLoggedIn()
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
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.sign_in))
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
                Text(
                    text = stringResource(id = R.string.sign_in),
                    color = White,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            presenter.authenticate(
                                username = username,
                                password = password
                            )
                        }
                        .background(Blue80)
                        .padding(10.dp)
                )
            }
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
@Preview
fun LoginScreenPreview() {
    val presenter = object : LoginScreenPresenter {
        override val state: StateFlow<LoginScreenState>
            get() = MutableStateFlow(LoginScreenState.Unauthorized())

    }
    DentalClinicTheme {
        LoginScreen(presenter = presenter)
    }
}