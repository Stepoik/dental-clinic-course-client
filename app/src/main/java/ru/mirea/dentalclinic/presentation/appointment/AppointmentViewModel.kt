package ru.mirea.dentalclinic.presentation.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.models.Appointment
import ru.mirea.dentalclinic.domain.models.Doctor
import ru.mirea.dentalclinic.domain.usecases.BookAppointmentUseCase
import ru.mirea.dentalclinic.domain.usecases.GetAppointmentsUseCase
import ru.mirea.dentalclinic.domain.usecases.GetDoctorByIdUseCase
import ru.mirea.dentalclinic.presentation.appointment.models.AppointmentVO
import ru.mirea.dentalclinic.presentation.common.formatters.DoctorFormatter
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.utils.day
import ru.mirea.dentalclinic.utils.decrease
import ru.mirea.dentalclinic.utils.increase
import java.util.Date

class AppointmentViewModel @AssistedInject constructor(
    @Assisted private val doctorId: Long,
    private val getAppointmentsUseCase: GetAppointmentsUseCase,
    private val appointmentVOMapper: AppointmentVOFormatter,
    private val getDoctorByIdUseCase: GetDoctorByIdUseCase,
    private val doctorFormatter: DoctorFormatter,
    private val bookAppointmentUseCase: BookAppointmentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AppointmentViewModelState())
    private var loadingJob: Job? = null

    val state: StateFlow<AppointmentScreenState>
        get() = _state.map { it.toScreenState(appointmentVOMapper, doctorFormatter) }
            .stateIn(viewModelScope, SharingStarted.Lazily, _state.value.toScreenState(appointmentVOMapper, doctorFormatter))

    init {
        updateHeader()
        loadAppointment()
    }

    fun bookAppointment(appointmentId: Long) {
        viewModelScope.launch {
            val appointmentResult = bookAppointmentUseCase.execute(appointmentId)
            appointmentResult.onFailure { _ ->
                _state.update { it.copy(errorMessage = "Не удалось забронировать") }
            }.onSuccess { loadAppointment() }
        }
    }

    fun pickPreviousDay() {
        _state.update { it.copy(selectedDate = it.selectedDate.decrease()) }
        loadAppointment()
    }

    fun pickNextDay() {
        _state.update { it.copy(selectedDate = it.selectedDate.increase()) }
        loadAppointment()
    }

    fun onErrorMessageShowed() {
        _state.update { it.copy(errorMessage = null) }
    }

    private fun updateHeader() {
        viewModelScope.launch {
            val doctorResult = getDoctorByIdUseCase.execute(doctorId)
            doctorResult.onSuccess { doctor ->
                _state.update {
                    it.copy(doctor = doctor.doctor)
                }
            }.onFailure { throwable ->
                _state.update {
                    it.copy(error = throwable)
                }
            }
        }
    }

    private fun loadAppointment() {
        _state.update { it.copy(isLoading = true, error = null) }
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            val appointmentsResult = getAppointmentsUseCase.execute(_state.value.selectedDate, doctorId)
            handleAppointmentResult(appointmentsResult)
        }
    }

    private fun handleAppointmentResult(appointmentsResult: Result<List<Appointment>>) {
        appointmentsResult.onSuccess { appointments ->
            _state.update { it.copy(appointments = appointments, isLoading = false) }
        }.onFailure { throwable ->
            _state.update { it.copy(error = throwable, isLoading = false) }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(doctorId: Long): AppointmentViewModel
    }
}

private data class AppointmentViewModelState(
    val selectedDate: Date = Date(),
    val doctor: Doctor? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val errorMessage: String? = null,
    val appointments: List<Appointment> = listOf()
) {
    fun toScreenState(mapper: AppointmentVOFormatter, doctorVOMapper: DoctorFormatter): AppointmentScreenState {
        return when {
            isLoading -> {
                AppointmentScreenState.Loading(
                    doctor = doctor?.let { doctorVOMapper.format(it) },
                    date = selectedDate.day().toString(),
                    errorMessage = errorMessage
                )
            }

            error != null -> {
                AppointmentScreenState.Error(
                    error = error,
                    doctor = doctor?.let { doctorVOMapper.format(it) },
                    date = selectedDate.day().toString(),
                    errorMessage = errorMessage
                )
            }

            appointments.isNotEmpty() -> {
                AppointmentScreenState.Success(
                    appointments = appointments.map(mapper::map),
                    doctor = doctor?.let { doctorVOMapper.format(it) },
                    date = selectedDate.day().toString(),
                    errorMessage = errorMessage
                )
            }

            else -> {
                AppointmentScreenState.Idle(
                    doctor = doctor?.let { doctorVOMapper.format(it) },
                    date = selectedDate.day().toString(),
                    errorMessage = errorMessage
                )
            }
        }
    }
}

sealed interface AppointmentScreenState {
    val doctor: DoctorVO?
    val date: String
    val errorMessage: String?

    data class Idle(
        override val doctor: DoctorVO?,
        override val date: String,
        override val errorMessage: String?
    ) : AppointmentScreenState

    data class Loading(
        override val doctor: DoctorVO?,
        override val date: String,
        override val errorMessage: String?
    ) : AppointmentScreenState

    data class Success(
        val appointments: List<AppointmentVO>,
        override val doctor: DoctorVO?,
        override val date: String,
        override val errorMessage: String?
    ) : AppointmentScreenState

    data class Error(
        val error: Throwable,
        override val doctor: DoctorVO?,
        override val date: String,
        override val errorMessage: String?
    ) : AppointmentScreenState
}