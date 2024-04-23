package ru.mirea.dentalclinic.domain.usecases

import ru.mirea.dentalclinic.domain.models.DoctorPage
import ru.mirea.dentalclinic.domain.repositories.DoctorRepository
import javax.inject.Inject

class SearchDoctorsUseCase @Inject constructor(
    private val doctorRepository: DoctorRepository
) {
    suspend fun execute(query: String, page: Int): Result<DoctorPage> {
        return doctorRepository.searchDoctors(
            query = query,
            page = page
        )
    }
}