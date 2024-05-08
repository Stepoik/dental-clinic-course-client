package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String
)
