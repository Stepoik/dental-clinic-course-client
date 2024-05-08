package ru.mirea.dentalclinic.presentation.proceduredoctors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.presentation.common.components.DoctorList
import ru.mirea.dentalclinic.presentation.common.components.Loading
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.ui.theme.Blue80
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme
import ru.mirea.dentalclinic.ui.theme.White

@Composable
fun ProcedureDoctorsScreen(presenter: ProcedureDoctorsPresenter) {
    val state = presenter.state.collectAsState().value
    val swipeState =
        rememberSwipeRefreshState(isRefreshing = state is ProcedureDoctorsState.Loading)
    LaunchedEffect(true) {
        presenter.update()
    }
    Scaffold { scaffoldPadding ->
        SwipeRefresh(
            state = swipeState, onRefresh = presenter::update, modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Column {
                Text(
                    text = state.procedureName,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(600),
                    color = Blue80
                )
                when (state) {
                    is ProcedureDoctorsState.Success -> {
                        DoctorList(
                            modifier = Modifier.fillMaxSize(),
                            doctors = state.doctors,
                            onItemClick = { doctorId -> presenter.navigateToDoctor(doctorId) }
                        )
                    }

                    is ProcedureDoctorsState.Error -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "Ошибка загрузки")
                        }
                    }

                    else -> {
                        Loading(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ProcedureDoctorsScreenPreview() {
    val doctorVO = DoctorVO(
        id = 1,
        image = "",
        name = "Степан",
        specialization = "Хайполог",
        experience = "2 года",
        rate = "4"
    )
    val presenter = object : ProcedureDoctorsPresenter {
        override val state: StateFlow<ProcedureDoctorsState>
            get() = MutableStateFlow(ProcedureDoctorsState.Success(procedureName = "Хайп", doctors = listOf(doctorVO)))

        override fun update() {}

        override fun navigateToDoctor(doctorId: Long) {}

    }
    DentalClinicTheme {
        ProcedureDoctorsScreen(presenter = presenter)
    }
}