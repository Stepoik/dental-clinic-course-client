package ru.mirea.dentalclinic.data.services

import retrofit2.http.GET
import retrofit2.http.Path
import ru.mirea.dentalclinic.data.models.DoctorsResponse

interface DoctorService {
    @GET("doctors/best/{page}")
    suspend fun getBestDoctors(@Path("page") page: Int = 0): DoctorsResponse
}