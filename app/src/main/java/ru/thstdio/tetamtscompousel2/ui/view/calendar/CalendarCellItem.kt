package ru.thstdio.tetamtscompousel2.ui.view.calendar

sealed class CalendarCellItem {
    object Empty : CalendarCellItem()
    data class WeekDay(val week: String) : CalendarCellItem()
    data class Day(val day: String) : CalendarCellItem()
    data class SelectedDay(val day: String) : CalendarCellItem()
}