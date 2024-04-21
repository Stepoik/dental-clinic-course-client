package ru.mirea.dentalclinic.homescreen.formatters

import android.content.Context
import ru.mirea.dentalclinic.R
import ru.mirea.dentalclinic.domain.models.Doctor
import ru.mirea.dentalclinic.homescreen.models.DoctorVO
import javax.inject.Inject

class DoctorFormatter @Inject constructor(
    private val context: Context
) {
    fun format(doctor: Doctor): DoctorVO {
        val doctorName = "${doctor.lastName} ${doctor.firstName[0]}.${doctor.middleName[0]}."
        val experience = context.resources.getQuantityString(
            R.plurals.year,
            doctor.experience,
            doctor.experience
        )
        return DoctorVO(
            image = doctor.image,
            name = doctorName,
            specialization = doctor.specialization,
            experience = experience,
            rate = doctor.rate.toString()
        )
    }
}