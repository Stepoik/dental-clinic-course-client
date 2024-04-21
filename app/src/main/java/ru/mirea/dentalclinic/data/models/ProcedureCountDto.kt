package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class ProcedureCountDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("doctor_count")
    val count: Int
)
