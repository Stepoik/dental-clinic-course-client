package ru.mirea.dentalclinic.data.services

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mirea.dentalclinic.data.models.DoctorDto
import ru.mirea.dentalclinic.data.models.DoctorsResponse

interface DoctorService {
    @GET("doctors/best/{page}")
    suspend fun getBestDoctors(@Path("page") page: Int = 0): DoctorsResponse

    @GET("doctors/list/{page}")
    suspend fun findDoctorsByName(
        @Path("page") page: Int,
        @Query("name") name: String
    ): DoctorsResponse

    @GET("doctors/{id}")
    suspend fun getDoctorById(
        @Path("id") id: Long
    ): DoctorDto

    @GET("doctors/procedure/{procedure}")
    suspend fun getDoctorsByProcedureId(
        @Path("procedure") procedureName: String
    ): DoctorsResponse
}