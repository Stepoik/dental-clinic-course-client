package ru.mirea.dentalclinic.utils

import java.util.Calendar
import java.util.Date

fun Date.day(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.DATE)
}

fun Date.increase(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, 1)
    return calendar.time
}

fun Date.decrease(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, -1)
    return calendar.time
}