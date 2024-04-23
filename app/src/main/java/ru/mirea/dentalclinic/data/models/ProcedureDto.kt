package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class ProcedureDto(
    @SerializedName("name")
    val name: String
)
