package ru.mirea.dentalclinic.domain.models

data class HomeInfo(
    val doctorPage: DoctorPage,
    val procedures: List<ProcedureCount>,
    val patient: Patient,
    val nearestAppointment: Appointment?
)
