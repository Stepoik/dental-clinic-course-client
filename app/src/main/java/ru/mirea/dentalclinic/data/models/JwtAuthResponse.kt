package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class JwtAuthResponse(
    @SerializedName("token")
    val token: String
)
