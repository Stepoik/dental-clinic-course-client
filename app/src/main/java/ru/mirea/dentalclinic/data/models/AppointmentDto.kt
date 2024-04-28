package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AppointmentDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("start_time")
    val start: Int,
    @SerializedName("end_time")
    val end: Int,
    @SerializedName("is_booked")
    val isBooked: Boolean,
    @SerializedName("day")
    val day: Date,
    @SerializedName("doctor")
    val doctor: DoctorDto
)
