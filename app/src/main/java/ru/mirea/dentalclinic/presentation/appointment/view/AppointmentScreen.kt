package ru.mirea.dentalclinic.presentation.appointment.view

import androidx.compose.animation.core.repeatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.appointment.AppointmentPresenter
import ru.mirea.dentalclinic.presentation.appointment.AppointmentScreenState
import ru.mirea.dentalclinic.presentation.appointment.models.AppointmentVO
import ru.mirea.dentalclinic.presentation.common.view.Loading
import ru.mirea.dentalclinic.ui.theme.Blue40
import ru.mirea.dentalclinic.ui.theme.Blue80
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme
import ru.mirea.dentalclinic.ui.theme.White

@Composable
fun AppointmentScreen(presenter: AppointmentPresenter) {
    val state = presenter.state.collectAsState().value
    val day = presenter.selectedDay.collectAsState().value
    Scaffold { scaffoldPadding ->
        Column(
            Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            DateHeader(date = day, presenter = presenter, modifier = Modifier.fillMaxWidth())
            when (state) {
                is AppointmentScreenState.Success -> {
                    AppointmentList(appointments = state.appointments)
                }

                is AppointmentScreenState.Loading -> {
                    Loading(modifier = Modifier.fillMaxSize())
                }

                else -> {
                    
                }
            }
        }
    }
}

@Composable
fun AppointmentList(appointments: List<AppointmentVO>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(appointments) { appointment ->
            AppointmentItem(appointment, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp))
        }
    }
}

@Composable
fun AppointmentItem(appointment: AppointmentVO, modifier: Modifier = Modifier) {
    val backgroundColor = if (appointment.isOpened) {
        Blue80
    } else {
        Color.Gray
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(10.dp), horizontalArrangement = Arrangement.Center
    ) {
        Text(text = appointment.time, color = White)
    }
}

@Composable
fun DateHeader(date: String, presenter: AppointmentPresenter, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(intrinsicSize = IntrinsicSize.Max),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.appointments), fontSize = 20.sp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = presenter::pickPreviousDay) {
                Icon(
                    modifier = Modifier.size(100.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous day"
                )
            }
            Text(text = date)
            IconButton(onClick = presenter::pickNextDay) {
                Icon(
                    modifier = Modifier.size(100.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next day"
                )
            }
        }
    }
}

@Composable
@Preview
private fun AppointmentListPreview() {
    val item = AppointmentVO(
        id = 0,
        time = "12:30 - 12:40",
        isOpened = true
    )
    DentalClinicTheme {
        AppointmentList(appointments = List(5) { item }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
@Preview
private fun AppointmentItemPreview() {
    val item = AppointmentVO(
        id = 0,
        time = "12:30 - 12:40",
        isOpened = true
    )
    DentalClinicTheme {
        AppointmentItem(appointment = item, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
@Preview
private fun AppointmentScreenPreview() {
    val item = AppointmentVO(
        id = 0,
        time = "12:30 - 12:40",
        isOpened = true
    )
    val presenter = object : AppointmentPresenter {
        override val selectedDay: StateFlow<String>
            get() = MutableStateFlow("")
        override val state: StateFlow<AppointmentScreenState>
            get() = MutableStateFlow(
                AppointmentScreenState.Success(
                    appointments = List(20) { item }
                )
            )
    }
    DentalClinicTheme {
        AppointmentScreen(presenter)
    }
}