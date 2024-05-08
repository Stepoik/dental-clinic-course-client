package ru.mirea.dentalclinic.presentation.doctorpage.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.common.components.Loading
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.presentation.doctorpage.models.ProcedureVO
import ru.mirea.dentalclinic.presentation.doctorpage.DoctorPagePresenter
import ru.mirea.dentalclinic.presentation.doctorpage.DoctorPageState
import ru.mirea.dentalclinic.ui.theme.Blue80
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme
import ru.mirea.dentalclinic.ui.theme.White

@Composable
fun DoctorPageScreen(presenter: DoctorPagePresenter) {
    val state = presenter.state.collectAsState().value
    val refreshState = rememberSwipeRefreshState(isRefreshing = state is DoctorPageState.Loading)
    Scaffold { scaffoldPadding ->
        SwipeRefresh(
            state = refreshState,
            onRefresh = presenter::update,
            modifier = Modifier
                .padding(scaffoldPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (state) {
                    is DoctorPageState.Success -> {
                        doctorInformation(
                            state = state,
                            presenter = presenter
                        )
                    }

                    is DoctorPageState.Loading -> {
                        item { Loading(modifier = Modifier.fillMaxSize()) }
                    }

                    is DoctorPageState.Error -> {
                        item { ErrorMessage(modifier = Modifier.fillMaxSize()) }
                    }

                    else -> {}
                }
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


fun LazyListScope.doctorInformation(
    state: DoctorPageState.Success,
    presenter: DoctorPagePresenter
) {
    item {
        Card(
            modifier = Modifier.padding(top = 20.dp),
            shape = CircleShape,
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp)
        ) {
            AsyncImage(
                model = state.doctor.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
        Text(text = state.doctor.name, modifier = Modifier.padding(top = 20.dp), fontSize = 26.sp)
        Text(
            text = stringResource(id = R.string.appointment),
            modifier = Modifier
                .padding(20.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = presenter::navigateToAppointment)
                .background(Blue80)
                .padding(10.dp),
            color = White
        )
    }
    items(state.procedures) { procedure ->
        ProcedureItem(
            procedure = procedure, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun ProcedureItem(procedure: ProcedureVO, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = procedure.name, color = Color.Black, fontSize = 20.sp)
        }
    }
}

@Composable
@Preview
fun DoctorPagePreview() {
    val procedure = ProcedureVO(
        name = "Чистка зубов"
    )
    val presenter = object : DoctorPagePresenter {
        override val state: StateFlow<DoctorPageState>
            get() = MutableStateFlow(
                DoctorPageState.Success(
                    doctor = DoctorVO(
                        id = 1,
                        image = "",
                        name = "Степан Горохов",
                        specialization = "Хирург",
                        experience = "4 года",
                        rate = "4"
                    ), procedures = listOf(procedure)
                )
            )

        override fun update() {}

        override fun navigateToAppointment() {}

    }
    DentalClinicTheme {
        DoctorPageScreen(presenter = presenter)
    }
}