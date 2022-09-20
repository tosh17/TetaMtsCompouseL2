package ru.thstdio.tetamtscompousel2.ui.view.calendar

import java.util.Calendar

const val DAY_IN_WEEK = 7
private val weekString = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
private val monthString = listOf(
    "Январь",
    "Февраль",
    "Март",
    "Апрель",
    "Май",
    "Июнь",
    "Июль",
    "Август",
    "Сентябрь",
    "Октябрь",
    "Ноябрь",
    "Декабрь"
)

fun CalendarViewParams.toViewStateList(): List<List<CalendarCellItem>> {
    val result = mutableListOf<CalendarCellItem>()
    result.addAll(
        weekString.map { weekDay -> CalendarCellItem.WeekDay(weekDay) }
    )

    val calendar = getCalendar()

    result.addAll(
        List<CalendarCellItem>(calendar.firstDayInMonth) {
            CalendarCellItem.Empty
        })
    result.addAll(List<CalendarCellItem>(calendar.dayOfMonth) { index ->
        val day = index + 1
        if (day == selectDay) {
            CalendarCellItem.SelectedDay(day.toString())
        } else {
            CalendarCellItem.Day(day.toString())
        }
    })
    if (result.size % DAY_IN_WEEK != 0) {
        result.addAll(List<CalendarCellItem>(DAY_IN_WEEK - result.size % DAY_IN_WEEK) {
            CalendarCellItem.Empty
        })
    }
    return result.toList().chunked(DAY_IN_WEEK)
}

fun CalendarViewParams.getMMYY(): String {
    return "${monthString[this.month - 1]} ${this.year}"
}

fun CalendarViewParams.getPrevMonth(): CalendarViewParams {
    val calendar = getCalendar().apply {
        add(Calendar.MONTH, -1)
    }
    return this.copy(
        month = calendar.get(Calendar.MONTH) + 1,
        year = calendar.get(Calendar.YEAR)
    )
}

fun CalendarViewParams.getNextMonth(): CalendarViewParams {
    val calendar = getCalendar().apply {
        add(Calendar.MONTH, 1)
    }
    return this.copy(
        month = calendar.get(Calendar.MONTH) + 1,
        year = calendar.get(Calendar.YEAR)
    )
}
fun getCurrentCalendarViewParams(): CalendarViewParams {
   val calendar =  Calendar.getInstance()
    return CalendarViewParams(
        month = calendar.get(Calendar.MONTH) + 1,
        year = calendar.get(Calendar.YEAR),
        selectDay = calendar.get(Calendar.DATE)
    )
}

private fun CalendarViewParams.getCalendar(): Calendar {
    return Calendar.getInstance().apply {
        set(year, month - 1, 1)
    }
}

private val Calendar.firstDayInMonth
    get() = run { (DAY_IN_WEEK + get(Calendar.DAY_OF_WEEK) - 2)% DAY_IN_WEEK }

private val Calendar.dayOfMonth
    get() = run { getActualMaximum(Calendar.DAY_OF_MONTH) }