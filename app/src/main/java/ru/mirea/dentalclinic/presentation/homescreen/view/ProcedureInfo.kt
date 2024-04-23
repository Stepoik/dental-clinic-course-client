package ru.mirea.dentalclinic.presentation.homescreen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.homescreen.HomeScreenState
import ru.mirea.dentalclinic.presentation.homescreen.models.ProcedureVO
import ru.mirea.dentalclinic.ui.theme.Blue40

@Composable
fun ProceduresInfo(modifier: Modifier = Modifier, homeScreenState: HomeScreenState) {
    Column(modifier = modifier) {
        CarouselHeader(title = stringResource(id = R.string.procedures))
        ProcedureCarousel(modifier = Modifier.padding(top = 28.dp), homeScreenState)
    }
}

@Composable
fun ProcedureCarousel(modifier: Modifier = Modifier, homeScreenState: HomeScreenState) {
    val procedures = homeScreenState.procedures
    LazyRow(modifier = modifier.padding(bottom = 10.dp)) {
        item {
            Spacer(modifier = modifier.padding(13.dp))
        }
        items(procedures) { procedure ->
            ProcedureCard(procedure = procedure, modifier = Modifier.padding(end = 16.dp))
        }
    }
}

@Composable
fun ProcedureCard(modifier: Modifier = Modifier, procedure: ProcedureVO) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val cardWidth = (screenWidth * 0.7).dp
    Card(
        modifier = modifier.width(cardWidth),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "card image",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, bottom = 20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Blue40)
                    .padding(13.dp)
                    .size(45.dp)
            )
            Column(Modifier.padding(start = 16.dp)) {
                Text(
                    text = procedure.name,
                    Modifier.padding(bottom = 12.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color.Black
                )
                Text(
                    text = procedure.doctorCount,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
@Preview
fun ProcedureInfoPreview() {
    val state = HomeScreenState(
        procedures = listOf(
            ProcedureVO(name = "Хирург", doctorCount = "15 докторов"),
            ProcedureVO(name = "Хирург", doctorCount = "15 докторов")
        )
    )
    ProceduresInfo(Modifier.fillMaxWidth(), state)
}
