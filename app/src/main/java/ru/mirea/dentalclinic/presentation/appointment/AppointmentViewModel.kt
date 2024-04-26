package ru.mirea.dentalclinic.presentation.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.usecases.GetAppointmentsUseCase
import ru.mirea.dentalclinic.presentation.appointment.models.AppointmentVO
import ru.mirea.dentalclinic.utils.day
import ru.mirea.dentalclinic.utils.decrease
import ru.mirea.dentalclinic.utils.increase
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class AppointmentViewModel @AssistedInject constructor(
    @Assisted private val doctorId: Long,
    private val getAppointmentsUseCase: GetAppointmentsUseCase,
    private val appointmentVOMapper: AppointmentVOMapper
) : ViewModel() {
    private val _state = MutableStateFlow<AppointmentScreenState>(AppointmentScreenState.Idle)
    private val _selectedDay = MutableStateFlow(Date())
    private var loadingJob: Job? = null

    val selectedDay: StateFlow<String>
        get() = _selectedDay.map { date ->
            date.day().toString()
        }.stateIn(viewModelScope, SharingStarted.Lazily, "")

    val state: StateFlow<AppointmentScreenState>
        get() = _state

    init {
        loadAppointment()
        viewModelScope.launch {
            _selectedDay.collect {
                loadAppointment()
            }
        }
    }

    private fun loadAppointment() {
        _state.value = AppointmentScreenState.Loading
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            val appointmentsResult = getAppointmentsUseCase.execute(_selectedDay.value, doctorId)
            appointmentsResult.onSuccess { appointments ->
                _state.value =
                    AppointmentScreenState.Success(appointments.map(appointmentVOMapper::map))
            }.onFailure {
                _state.value = AppointmentScreenState.Error(it)
            }
        }
    }

    fun pickPreviousDay() {
        _selectedDay.update { it.decrease() }
    }

    fun pickNextDay() {
        _selectedDay.update { it.increase() }
    }

    @AssistedFactory
    interface Factory {
        fun create(doctorId: Long): AppointmentViewModel
    }
}

sealed class AppointmentScreenState {
    object Idle : AppointmentScreenState()
    object Loading : AppointmentScreenState()
    data class Success(val appointments: List<AppointmentVO>) : AppointmentScreenState()
    data class Error(val error: Throwable) : AppointmentScreenState()
}