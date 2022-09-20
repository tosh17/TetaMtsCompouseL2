package ru.thstdio.tetamtscompousel2.ui.view.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarView(
    params: MutableState<CalendarViewParams>,
) {
    Column(modifier = Modifier.padding(start = 20.dp, end = 16.dp)) {
        MonthInfoRow(
            mmyy = params.value.getMMYY(),
            onClickPrevMonth = { params.value = params.value.getPrevMonth() },
            onClickNextMonth = { params.value = params.value.getNextMonth() }
        )
        CalendarTable(
            params = params.value,
            onSelect = { selectDay -> params.value = params.value.copy(selectDay = selectDay.toIntOrNull()) })
    }
}

@Composable
private fun MonthInfoRow(
    mmyy: String,
    onClickPrevMonth: () -> Unit,
    onClickNextMonth: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = mmyy,
            fontSize = 20.sp,
            fontWeight = FontWeight(500),
            color = Color.Black
        )

        Row {
            Icon(
                modifier = Modifier
                    .clickable { onClickPrevMonth() }
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = "prev_month"
            )
            Icon(
                modifier = Modifier
                    .clickable { onClickNextMonth() }
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "next_month"
            )
        }
    }

}

data class CalendarViewParams(
    val month: Int,
    val year: Int,
    val selectDay: Int?
) {
    companion object {
        fun getCurrentDate(): CalendarViewParams = getCurrentCalendarViewParams()
    }
}