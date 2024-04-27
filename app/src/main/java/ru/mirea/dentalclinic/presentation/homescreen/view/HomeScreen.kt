package ru.mirea.dentalclinic.presentation.homescreen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.homescreen.HomeScreenPresenter
import ru.mirea.dentalclinic.presentation.homescreen.HomeScreenState
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.presentation.doctorlist.view.navigateToDoctorList
import ru.mirea.dentalclinic.presentation.homescreen.models.PatientVO
import ru.mirea.dentalclinic.presentation.homescreen.models.ProcedureVO
import ru.mirea.dentalclinic.ui.theme.Blue40
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme

@Composable
fun HomeScreen(presenter: HomeScreenPresenter) {
    val state by presenter.state.collectAsState()
    val scrollState = rememberScrollState()
    val refreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    Scaffold { scaffoldPadding ->
        SwipeRefresh(state = refreshState, onRefresh = { presenter.update() }) {
            Column(
                Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .verticalScroll(scrollState)
            ) {
                PatientInfo(state = state)
                ProceduresInfo(modifier = Modifier.padding(top = 40.dp), homeScreenState = state)
                BestDoctorsInfo(
                    presenter = presenter,
                    state = state,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }
    }
}

@Composable
fun PatientInfo(modifier: Modifier = Modifier, state: HomeScreenState) {
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
                color = Color.Black
            )
        }
    }
}

@Composable
fun BestDoctorsInfo(
    modifier: Modifier = Modifier,
    presenter: HomeScreenPresenter,
    state: HomeScreenState
) {
    Column(modifier = modifier) {
        CarouselHeader(title = stringResource(id = R.string.best_doctors), onShowAllClicked = {
            presenter.navigateToDoctorList()
        })
        DoctorCarousel(modifier = Modifier.padding(top = 28.dp), state, presenter)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoctorCarousel(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    presenter: HomeScreenPresenter
) {
    val doctors = state.bestDoctors
    val lazyState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyState),
        state = lazyState
    ) {
        item {
            Spacer(modifier = Modifier.padding(12.dp))
        }
        items(doctors) { doctor ->
            DoctorCard(
                doctor,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable(onClick = { presenter.navigateToDoctorPage(doctor.id) })
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DoctorCard(doctor: DoctorVO, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val cardWidth = (screenWidth * 0.7).dp
    Card(
        modifier = modifier.defaultMinSize(minWidth = cardWidth),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = doctor.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, bottom = 20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .size(height = 120.dp, width = 100.dp)
                    .background(Color.White)
            )
            Column(Modifier.padding(start = 16.dp)) {
                Text(
                    text = doctor.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Blue40
                )
                Text(
                    text = doctor.specialization,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
                FlowRow(
                    Modifier.padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DoctorInformation(
                        painter = painterResource(id = R.drawable.backpack),
                        text = doctor.experience
                    )
                    DoctorInformation(
                        painter = rememberVectorPainter(Icons.Filled.Star),
                        text = doctor.rate
                    )
                }
            }
        }
    }
}

@Composable
fun DoctorInformation(painter: Painter, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painter, contentDescription = text, Modifier.size(20.dp))
        Text(
            text = text,
            modifier = Modifier.padding(start = 4.dp),
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight(400)
        )
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
}

@Composable
@Preview
fun BestDoctorsInfoPreview() {
    val state = HomeScreenState(
        bestDoctors = listOf(
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
        BestDoctorsInfo(modifier = Modifier.fillMaxWidth(), defaultPresenter(state), state)
    }
}

@Composable
@Preview
fun PatientInfoPreview() {
    val state = HomeScreenState()
    PatientInfo(Modifier.fillMaxWidth(), state)
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