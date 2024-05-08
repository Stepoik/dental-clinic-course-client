package ru.mirea.dentalclinic.data.models

import com.google.gson.annotations.SerializedName

data class DoctorDto(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("middle_name")
    val middleName: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("specialization")
    val specialization: DoctorSpecializationDto?,
    @SerializedName("rate")
    val rate: Float?,
    @SerializedName("experience")
    val experience: Int?
)
