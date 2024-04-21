package ru.mirea.dentalclinic.data.services

import retrofit2.http.GET
import ru.mirea.dentalclinic.data.models.ProcedureCountResponse

interface ProcedureService {
    @GET("/procedure")
    suspend fun getProcedures(): ProcedureCountResponse
}