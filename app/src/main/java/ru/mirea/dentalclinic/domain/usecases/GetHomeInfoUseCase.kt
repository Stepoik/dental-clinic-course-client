package ru.mirea.dentalclinic.domain.usecases

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.asCompletableFuture
import ru.mirea.dentalclinic.domain.models.HomeInfo
import javax.inject.Inject

class GetHomeInfoUseCase @Inject constructor(
    private val getPatientInfoUseCase: GetPatientInfoUseCase,
    private val getBestDoctorsUseCase: GetBestDoctorsUseCase,
    private val getProceduresUseCase: GetProceduresUseCase,
    private val getNearestAppointmentUseCase: GetNearestAppointment
) {
    suspend fun execute(): Result<HomeInfo> = coroutineScope {
        val doctorsJob = async { getBestDoctorsUseCase.execute() }
        val procedureJob = async { getProceduresUseCase.execute() }
        val patientJob = async { getPatientInfoUseCase.execute() }
        val nearestAppointmentJob = async { getNearestAppointmentUseCase.execute() }

        val doctorsPage = doctorsJob.await().getOrElse { exception -> return@coroutineScope Result.failure(exception) }
        val procedures = procedureJob.await().getOrElse { exception -> return@coroutineScope Result.failure(exception) }
        val patient = patientJob.await().getOrElse { exception -> return@coroutineScope Result.failure(exception) }
        val nearestAppointment = nearestAppointmentJob.await().getOrElse { exception -> return@coroutineScope Result.failure(exception) }
        return@coroutineScope Result.success(HomeInfo(
            doctorPage = doctorsPage,
            procedures = procedures,
            patient = patient,
            nearestAppointment = nearestAppointment
        ))
    }
}