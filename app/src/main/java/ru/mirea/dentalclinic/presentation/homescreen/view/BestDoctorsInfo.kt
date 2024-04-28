package ru.mirea.dentalclinic.presentation.homescreen.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.presentation.homescreen.HomeScreenPresenter
import ru.mirea.dentalclinic.presentation.homescreen.HomeScreenState
import ru.mirea.dentalclinic.ui.theme.Blue40
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme

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
        DoctorCarousel(modifier = Modifier.padding(top = 28.dp).fillMaxSize(), state, presenter)
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
        BestDoctorsInfo(modifier = Modifier.background(Color.White).fillMaxWidth(), defaultPresenter(state), state)
    }
}