package ru.thstdio.tetamtscompousel2.ui.view.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.thstdio.tetamtscompousel2.ui.theme.MtsDefault


private val cellHeight = 32.dp

@Composable
fun CalendarTable(params: CalendarViewParams, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val weekList = params.toViewStateList()
        (0 until DAY_IN_WEEK).forEach { indexWeek ->
            Column(horizontalAlignment = CenterHorizontally) {
                weekList.indices.map { indexDay -> weekList[indexDay][indexWeek] }
                    .map { item -> CalendarCellItem(item,onSelect) }
            }
        }
    }
}

@Composable
private fun CalendarCellItem(item: CalendarCellItem, onSelect: (String) -> Unit) {
    when (item) {
        is CalendarCellItem.Day -> DayCell(item,onSelect)
        CalendarCellItem.Empty -> EmptyCell()
        is CalendarCellItem.SelectedDay -> SelectedDayCell(item)
        is CalendarCellItem.WeekDay -> WeekCell(item)
    }
}

@Composable
private fun WeekCell(item: CalendarCellItem.WeekDay) {
    Text(
        modifier = Modifier.height(cellHeight),
        text = item.week,
        fontSize = 12.sp,
        color = Color.Gray
    )
}

@Composable
private fun EmptyCell() {
    Spacer(modifier = Modifier.height(cellHeight))
}

@Composable
private fun DayCell(item: CalendarCellItem.Day, onSelect: (String) -> Unit) {
    Text(
        modifier = Modifier
            .height(cellHeight)
            .clickable { onSelect(item.day) },
        text = item.day,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun SelectedDayCell(item: CalendarCellItem.SelectedDay) {
    Text(
        modifier = Modifier.height(cellHeight),
        text = item.day,
        color = MtsDefault
    )
}

