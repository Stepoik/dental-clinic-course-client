package ru.mirea.dentalclinic.presentation.doctorlist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.presentation.common.view.Loading
import ru.mirea.dentalclinic.presentation.doctorlist.DoctorListPresenter
import ru.mirea.dentalclinic.presentation.doctorlist.DoctorListState
import ru.mirea.dentalclinic.ui.theme.Blue40
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme

@Composable
fun DoctorListScreen(doctorListPresenter: DoctorListPresenter) {
    val state = doctorListPresenter.state.collectAsState().value
    var query by remember {
        mutableStateOf("")
    }
    Scaffold { scaffoldPadding ->
        Column(
            Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 2.dp),
                value = query,
                onValueChange = {
                    query = it
                    doctorListPresenter.onQueryChanged(query)
                }
            )
            when (state) {
                is DoctorListState.Success -> {
                    DoctorList(
                        doctors = state.doctors,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .weight(1f),
                        presenter = doctorListPresenter
                    )
                }

                is DoctorListState.Loading -> {
                    Loading(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }

                else -> {

                }
            }
        }
    }
}

@Composable
fun DoctorList(
    modifier: Modifier = Modifier,
    doctors: List<DoctorVO>,
    presenter: DoctorListPresenter
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        item {
            Spacer(modifier = Modifier)
        }
        items(doctors) { doctor ->
            DoctorItem(
                doctor = doctor,
                modifier = Modifier.clickable { presenter.navigateToDoctorPage(doctor.id) })
        }
    }
}

@Composable
fun DoctorItem(modifier: Modifier = Modifier, doctor: DoctorVO) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp),
        modifier = modifier.padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(vertical = 10.dp)
        ) {
            AsyncImage(
                model = doctor.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            )
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = doctor.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = doctor.specialization)
            }
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Star, contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = doctor.rate)
                }
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.backpack),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(text = doctor.experience)
                }
            }
        }
    }
}

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Blue40,
            unfocusedBorderColor = Blue40,
            cursorColor = Color.Black
        )
    )
}


@Composable
@Preview
private fun DoctorListScreenPreview() {
    val doctorMock = DoctorVO(
        id = 0,
        name = "Горохов С.В.",
        specialization = "Стоматолог",
        experience = "4 года",
        rate = "4,7",
        image = ""
    )
    val state = MutableStateFlow(
        DoctorListState.Success(
            listOf(doctorMock, doctorMock),
            query = "",
            page = 1
        )
    )
    val presenter = object : DoctorListPresenter {
        override val state: StateFlow<DoctorListState>
            get() = state

        override fun onQueryChanged(query: String) {}
        override fun nextPage() {}
        override fun navigateToDoctorPage(doctorId: Long) {}
    }
    DentalClinicTheme {
        DoctorListScreen(presenter)
    }
}

@Composable
@Preview
private fun DoctorListScreenLoadingPreview() {
    val state = MutableStateFlow(DoctorListState.Loading)
    val presenter = object : DoctorListPresenter {
        override val state: StateFlow<DoctorListState>
            get() = state

        override fun onQueryChanged(query: String) {}
        override fun nextPage() {}
        override fun navigateToDoctorPage(doctorId: Long) {}
    }
    DentalClinicTheme {
        DoctorListScreen(presenter)
    }
}
