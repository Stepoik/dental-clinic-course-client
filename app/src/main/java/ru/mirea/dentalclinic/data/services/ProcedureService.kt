package ru.mirea.dentalclinic.data.services

import retrofit2.http.GET
import retrofit2.http.Path
import ru.mirea.dentalclinic.data.models.ProcedureCountResponse
import ru.mirea.dentalclinic.data.models.ProcedureResponse

interface ProcedureService {
    @GET("/procedure")
    suspend fun getProcedures(): ProcedureCountResponse

    @GET("/procedure/doctor/{doctorId}")
    suspend fun getProceduresByDoctorId(
        @Path("doctorId") doctorId: Long
    ): ProcedureResponse
}