package ru.mirea.dentalclinic.presentation.homescreen.formatters

import ru.mirea.dentalclinic.domain.models.Patient
import ru.mirea.dentalclinic.presentation.homescreen.models.PatientVO
import javax.inject.Inject

class PatientFormatter @Inject constructor() {
    fun format(patient: Patient): PatientVO {
        val patientName = "${patient.firstName} ${patient.lastName}"
        return PatientVO(
            image = "",
            patientName
        )
    }
}