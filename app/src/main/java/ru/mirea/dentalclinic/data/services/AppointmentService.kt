package ru.mirea.dentalclinic.data.services

import retrofit2.http.GET
import retrofit2.http.Path
import ru.mirea.dentalclinic.data.models.AppointmentDto
import ru.mirea.dentalclinic.data.models.AppointmentResponse
import java.util.Date

interface AppointmentService {
    @GET("/record/date/{date}/{doctorId}")
    suspend fun getRecordByDateAndDoctorId(
        @Path("date") date: String,
        @Path("doctorId") doctorId: Long
    ): AppointmentResponse
}