package ru.mirea.dentalclinic.data.models

enum class DoctorSpecializationDto(val specialization: String) {
    Therapist("Терапевт"),
    Surgeon("Хирург"),
    Orthopedist("Ортопед"),
    Hygienist("Гигиенист"),
    Periodontist("Пародонтолог"),
    Implantologist("Имплантолог"),
    Orthodontist("Ортодонт");
}