package ru.mirea.dentalclinic.data.services

import retrofit2.http.GET
import ru.mirea.dentalclinic.data.models.PatientDto

interface PatientService {
    @GET("/patient")
    suspend fun getPatient(): PatientDto
}