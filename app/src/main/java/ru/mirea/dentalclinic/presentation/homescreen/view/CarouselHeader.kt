package ru.mirea.dentalclinic.presentation.homescreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.ui.theme.Blue40

@Composable
fun CarouselHeader(modifier: Modifier = Modifier, title: String, onShowAllClicked: () -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight(600)
        )
        Text(
            text = stringResource(id = R.string.show_all),
            fontSize = 12.sp,
            fontWeight = FontWeight(400),
            color = Blue40,
            modifier = Modifier.clickable(onClick = onShowAllClicked)
        )
    }
}