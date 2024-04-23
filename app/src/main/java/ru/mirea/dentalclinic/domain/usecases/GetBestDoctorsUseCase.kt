package ru.mirea.dentalclinic.domain.usecases

import ru.mirea.dentalclinic.domain.models.Doctor
import ru.mirea.dentalclinic.domain.models.DoctorPage
import ru.mirea.dentalclinic.domain.repositories.DoctorRepository
import javax.inject.Inject

class GetBestDoctorsUseCase @Inject constructor(
    private val repository: DoctorRepository
) {
    suspend fun execute(page: Int = 0): Result<DoctorPage> {
        return repository.getSortedByRateDoctors(page)
    }
}