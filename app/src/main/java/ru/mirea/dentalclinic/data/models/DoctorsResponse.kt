package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class DoctorsResponse(
    @SerializedName("doctors")
    val doctors: List<DoctorDto>
)
