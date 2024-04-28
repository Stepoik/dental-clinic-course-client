package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class PatientDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String
)
