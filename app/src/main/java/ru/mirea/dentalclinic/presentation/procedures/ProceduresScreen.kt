package ru.mirea.dentalclinic.presentation.procedures

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.presentation.common.components.Loading
import ru.mirea.dentalclinic.presentation.common.models.ProcedureVO
import ru.mirea.dentalclinic.ui.theme.Blue40
import ru.mirea.dentalclinic.ui.theme.Blue80
import ru.mirea.dentalclinic.ui.theme.DentalClinicTheme

@Composable
fun ProceduresScreen(presenter: ProceduresPresenter) {
    val state = presenter.state.collectAsState().value
    val swipeState = rememberSwipeRefreshState(isRefreshing = state is ProceduresState.Loading)
    LaunchedEffect(true) {
        presenter.update()
    }
    Scaffold { scaffoldPadding ->
        SwipeRefresh(
            state = swipeState,
            onRefresh = presenter::update,
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            when (state) {
                is ProceduresState.Success -> {
                    ProcedureList(
                        modifier = Modifier.fillMaxSize(),
                        procedures = state.procedures,
                        onItemClick = presenter::navigateToDoctorsWithProcedure
                    )
                }

                is ProceduresState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Ошибка при загрузке", fontSize = 26.sp, color = Blue40)
                    }
                }

                else -> {
                    Loading(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun ProcedureList(
    procedures: List<ProcedureVO>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {}
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        item {
            Spacer(modifier = Modifier.padding(10.dp))
        }
        item {
            Text(
                text = stringResource(id = R.string.procedures),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(600),
                color = Blue80
            )
        }
        items(procedures) { procedure ->
            ProcedureItem(
                procedure = procedure,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun ProcedureItem(
    procedure: ProcedureVO,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(IntrinsicSize.Max),
        onClick = { onItemClick(procedure.name) },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = procedure.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 2.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(text = procedure.doctorCount, fontSize = 20.sp, modifier = Modifier)
        }
    }
}

@Preview
@Composable
fun ProcedureItemPreview() {
    DentalClinicTheme {
        ProcedureItem(
            procedure = ProcedureVO(
                name = "Удаление зуба",
                doctorCount = "4 доктора"
            ), modifier = Modifier.fillMaxWidth()
        )
    }
}