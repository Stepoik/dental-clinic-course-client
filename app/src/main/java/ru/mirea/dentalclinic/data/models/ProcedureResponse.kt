package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class ProcedureResponse(
    @SerializedName("procedures")
    val procedures: List<ProcedureDto>
)
