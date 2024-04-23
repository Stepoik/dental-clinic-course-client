package ru.mirea.dentalclinic.presentation.doctorpage.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.common.view.Loading
import ru.mirea.dentalclinic.presentation.doctorpage.DoctorPagePresenter
import ru.mirea.dentalclinic.presentation.doctorpage.DoctorPageState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorPageScreen(presenter: DoctorPagePresenter) {
    val state = presenter.state.collectAsState().value
    Scaffold { scaffoldPadding ->
        Column(
            Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            when (state) {
                is DoctorPageState.Success -> {
                    DoctorInformation(state, modifier = Modifier.fillMaxSize())
                }

                is DoctorPageState.Loading -> {
                    Loading(modifier = Modifier.fillMaxSize())
                }

                is DoctorPageState.Error -> {
                    ErrorMessage(modifier = Modifier.fillMaxSize())
                }

                else -> {}
            }
        }
    }
}

@Composable
fun ErrorMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.network_error))
    }
}

@Composable
fun DoctorInformation(state: DoctorPageState.Success, modifier: Modifier = Modifier) {
    Text(text = state.doctor.name)
    for (i in state.procedures) {
        Text(text = i.name)
    }
}