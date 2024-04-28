package ru.mirea.dentalclinic.data.services

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.mirea.dentalclinic.data.models.AppointmentDto
import ru.mirea.dentalclinic.data.models.AppointmentResponse
import ru.mirea.dentalclinic.presentation.appointment.models.AppointmentVO
import java.util.Date

interface AppointmentService {
    @GET("/record/date/{date}/{doctorId}")
    suspend fun getAppointmentsByDateAndDoctorId(
        @Path("date") date: String,
        @Path("doctorId") doctorId: Long
    ): AppointmentResponse

    @POST("/record/book/{id}")
    suspend fun bookAppointment(@Path("id") appointmentId: Long): AppointmentDto

    @GET("/record")
    suspend fun getUserAppointments(): AppointmentResponse
}