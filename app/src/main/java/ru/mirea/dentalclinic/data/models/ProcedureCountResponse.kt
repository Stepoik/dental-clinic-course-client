package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class ProcedureCountResponse(
    @SerializedName("procedures")
    val procedures: List<ProcedureCountDto>
)
