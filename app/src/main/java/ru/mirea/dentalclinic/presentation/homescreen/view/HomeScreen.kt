package ru.mirea.dentalclinic.presentation.homescreen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.domain.models.Appointment
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.presentation.homescreen.HomeScreenPresenter
import ru.mirea.dentalclinic.presentation.homescreen.HomeScreenState
import ru.mirea.dentalclinic.presentation.homescreen.models.HomeAppointmentVO
import ru.mirea.dentalclinic.presentation.homescreen.models.PatientVO
import ru.mirea.dentalclinic.presentation.homescreen.models.ProcedureVO
import ru.mirea.dentalclinic.ui.theme.Blue40
import ru.mirea.dentalclinic.ui.theme.Blue80
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme

@Composable
fun HomeScreen(presenter: HomeScreenPresenter) {
    val state = presenter.state.collectAsState().value
    val scrollState = rememberScrollState()
    val refreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    LaunchedEffect(state.isLogged) {
        if (!state.isLogged) {
            presenter.navigateToAuth()
        }
    }
    Scaffold { scaffoldPadding ->
        SwipeRefresh(state = refreshState, onRefresh = { presenter.update() }) {
            Column(
                Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .verticalScroll(scrollState)
            ) {
                PatientInfo(state = state, presenter = presenter)
                if (state.appointment != null) {
                    AppointmentInfo(
                        appointment = state.appointment,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                ProceduresInfo(modifier = Modifier.padding(top = 40.dp), homeScreenState = state)
                BestDoctorsInfo(
                    presenter = presenter,
                    state = state,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun PatientInfo(
    state: HomeScreenState,
    presenter: HomeScreenPresenter,
    modifier: Modifier = Modifier
) {
    val patientName = state.patient?.patientName ?: ""
    Row(modifier = modifier.padding(25.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            imageVector = Icons.Filled.AccountCircle, contentDescription = "Patient",
            modifier = Modifier
                .weight(1f)
                .size(60.dp)
        )
        Column(modifier = Modifier.weight(5f)) {
            Text(
                text = stringResource(id = R.string.patient),
                color = Color.Gray, modifier = Modifier.padding(bottom = 6.dp), fontSize = 15.sp
            )
            Text(
                text = patientName,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = stringResource(id = R.string.logout),
            color = Blue40,
            modifier = Modifier.clickable(onClick = presenter::logout)
        )
    }
}

@Composable
fun AppointmentInfo(appointment: HomeAppointmentVO, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .padding(horizontal = 25.dp)
    ) {
        Text(
            text = stringResource(id = R.string.nearest_appointment),
            fontSize = 18.sp,
            fontWeight = FontWeight(600)
        )
        Card(
            modifier = Modifier.padding(top = 28.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp)
        ) {
            Column(
                Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(text = appointment.doctorName, color = Blue40, maxLines = 1)
                Text(text = appointment.time, color = Blue40)
            }
        }
    }
}

@Composable
@Preview
fun AppointmentInfoPreview() {
    val homeAppointment = HomeAppointmentVO(
        time = "23:00 - 24:00",
        doctorName = "Степан Горохов"
    )
    DentalClinicTheme {
        AppointmentInfo(appointment = homeAppointment, modifier = Modifier.fillMaxWidth())
    }
}


fun defaultPresenter(state: HomeScreenState) = object : HomeScreenPresenter {
    override val state: StateFlow<HomeScreenState>
        get() = MutableStateFlow(state)

    override fun update() {}
    override fun navigateToDoctorList() {}
    override fun navigateToDoctorPage(doctorId: Long) {}
    override fun navigateToAppointment(doctorId: Long) {}
    override fun onErrorShowed() {}
    override fun logout() {}
    override fun navigateToAuth() {}
}

@Composable
@Preview
fun PatientInfoPreview() {
    val state = HomeScreenState()
    PatientInfo(modifier = Modifier.fillMaxWidth(), presenter = defaultPresenter(state), state = state)
}

@Composable
@Preview
private fun HomeScreenPreview() {
    val state = HomeScreenState(
        patient = PatientVO(image = "", patientName = "Степан Горох"), procedures = listOf(
            ProcedureVO(name = "Хирург", doctorCount = "15 докторов"),
            ProcedureVO(name = "Хирург", doctorCount = "15 докторов")
        ),
        bestDoctors = listOf(
            DoctorVO(
                id = 0,
                name = "Горохов С.В.",
                specialization = "Стоматолог",
                experience = "4 года",
                rate = "4,7",
                image = ""
            ),
            DoctorVO(
                id = 0,
                name = "Горохов С.В.",
                specialization = "Стоматолог",
                experience = "4 года",
                rate = "4,7",
                image = ""
            )
        )
    )
    DentalClinicTheme {
        HomeScreen(presenter = defaultPresenter(state))
    }
}