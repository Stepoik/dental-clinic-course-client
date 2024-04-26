package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    @SerializedName("records")
    val appointments: List<AppointmentDto>
)