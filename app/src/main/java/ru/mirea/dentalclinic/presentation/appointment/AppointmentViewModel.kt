package ru.mirea.dentalclinic.presentation.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.presentation.appointment.models.AppointmentVO
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class AppointmentViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow<AppointmentScreenState>(AppointmentScreenState.Idle)
    private val _selectedDay = MutableStateFlow(Date())
    private var loadingJob: Job? = null


    val selectedDay: StateFlow<String>
        get() = _selectedDay.map { date ->
            date.day().toString()
        }.stateIn(viewModelScope, SharingStarted.Lazily, "")

    val state: StateFlow<AppointmentScreenState>
        get() = _state.onSubscription {
            loadAppointment()
        }.stateIn(viewModelScope, SharingStarted.Lazily, _state.value)

    private fun loadAppointment() {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {

        }
    }

    fun pickPreviousDay() {
        _selectedDay.update { it.decrease() }
    }

    fun pickNextDay() {
        _selectedDay.update { it.increase() }
    }
}

fun Date.day(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.DATE)
}

fun Date.increase(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, 1)
    return calendar.time
}

fun Date.decrease(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, -1)
    return calendar.time
}

sealed class AppointmentScreenState {
    object Idle : AppointmentScreenState()
    object Loading : AppointmentScreenState()
    data class Success(val appointments: List<AppointmentVO>) : AppointmentScreenState()
    data class Error(val error: Throwable) : AppointmentScreenState()
}