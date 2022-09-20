package ru.thstdio.tetamtscompousel2

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.thstdio.tetamtscompousel2.ui.theme.MtsDefault
import ru.thstdio.tetamtscompousel2.ui.view.calendar.CalendarView
import ru.thstdio.tetamtscompousel2.ui.view.calendar.CalendarViewParams

@Composable
fun CalendarScreen() {
    var date = remember { mutableStateOf(CalendarViewParams.getCurrentDate()) }
    var currentPage by remember { mutableStateOf(CalendarPageType.Calendar) }

    Crossfade(targetState = currentPage) { screen ->
        when (screen) {
            CalendarPageType.Calendar -> CalendarPage(
                date = date,
                openDetailPage = { currentPage = CalendarPageType.Detail }
            )
            CalendarPageType.Detail -> DetailPage(date)
        }
    }
}

@Composable
fun CalendarPage(
    date: MutableState<CalendarViewParams>,
    openDetailPage: () -> Unit
) {
    Column {
        CalendarView(date)
        Button(
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 64.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),

            onClick = { openDetailPage() }) {
            Text("Открыть расписание")
        }
    }
}

@Composable
fun DetailPage(date: MutableState<CalendarViewParams>) {
    Column(modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Планы на ${date.value.selectDay} число")
        (0..5).forEach {
            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MtsDefault)
                )
                Text(text = "Сделать дело номер $it")
            }

        }
    }
}


private enum class CalendarPageType {
    Calendar, Detail
}