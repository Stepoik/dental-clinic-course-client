package ru.mirea.dentalclinic.presentation.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.repositories.AuthLocalRepository
import ru.mirea.dentalclinic.domain.usecases.GetHomeInfoUseCase
import ru.mirea.dentalclinic.presentation.common.formatters.DoctorFormatter
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.presentation.homescreen.formatters.HomeAppointmentFormatter
import ru.mirea.dentalclinic.presentation.homescreen.formatters.PatientFormatter
import ru.mirea.dentalclinic.presentation.common.models.ProcedureFormatter
import ru.mirea.dentalclinic.presentation.homescreen.models.HomeAppointmentVO
import ru.mirea.dentalclinic.presentation.homescreen.models.PatientVO
import ru.mirea.dentalclinic.presentation.common.models.ProcedureVO
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getHomeInfoUseCase: GetHomeInfoUseCase,
    private val doctorFormatter: DoctorFormatter,
    private val procedureFormatter: ProcedureFormatter,
    private val patientFormatter: PatientFormatter,
    private val homeAppointmentFormatter: HomeAppointmentFormatter,
    private val authLocalRepository: AuthLocalRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())

    val state: StateFlow<HomeScreenState>
        get() = _state

    fun onErrorShowed() {
        _state.update { it.copy(error = null) }
    }

    fun logout() {
        viewModelScope.launch {
            authLocalRepository.logout()
            _state.update { it.copy(isLogged = false) }
        }
    }

    fun update() {
        if (state.value.isLoading) {
            return
        }
        _state.update { currentState ->
            currentState.copy(
                isLoading = true,
                error = null
            )
        }
        viewModelScope.launch {
            val homeInfoResult = getHomeInfoUseCase.execute()
            val homeInfo = homeInfoResult.onError { return@launch }
            _state.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    patient = patientFormatter.format(homeInfo.patient),
                    bestDoctors = homeInfo.doctorPage.doctors.map(doctorFormatter::format),
                    procedures = homeInfo.procedures.map(procedureFormatter::format),
                    appointment = homeInfo.nearestAppointment?.let {
                        homeAppointmentFormatter.format(
                            it
                        )
                    }
                )
            }
        }
    }

    private inline fun <R, T : R> Result<T>.onError(body: (Throwable) -> R): R {
        return getOrElse {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    error = it
                )
            }
            return body.invoke(it)
        }
    }
}

data class HomeScreenState(
    val isLogged: Boolean = true,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val patient: PatientVO? = null,
    val appointment: HomeAppointmentVO? = null,
    val procedures: List<ProcedureVO> = listOf(),
    val bestDoctors: List<DoctorVO> = listOf()
)
