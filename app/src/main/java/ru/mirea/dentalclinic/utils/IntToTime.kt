package ru.mirea.dentalclinic.utils

import java.time.LocalTime

fun Int.asTime(): LocalTime {
    val startHour = this / 60
    val startMinute = this % 60
    return LocalTime.of(startHour, startMinute)
}