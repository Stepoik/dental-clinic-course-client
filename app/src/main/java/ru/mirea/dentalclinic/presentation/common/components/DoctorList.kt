package ru.mirea.dentalclinic.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO

@Composable
fun DoctorList(
    modifier: Modifier = Modifier,
    doctors: List<DoctorVO>,
    onItemClick: (Long) -> Unit,
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        item {
            Spacer(modifier = Modifier)
        }
        items(doctors) { doctor ->
            DoctorItem(
                doctor = doctor,
                onItemClick = { onItemClick.invoke(doctor.id) }
            )
        }
    }
}

@Composable
fun DoctorItem(modifier: Modifier = Modifier, doctor: DoctorVO, onItemClick: (Long) -> Unit) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp),
        modifier = modifier.padding(horizontal = 10.dp),
        onClick = { onItemClick(doctor.id) }
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