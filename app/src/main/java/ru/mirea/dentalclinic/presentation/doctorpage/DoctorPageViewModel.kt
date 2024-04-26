package ru.mirea.dentalclinic.presentation.doctorpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.usecases.GetDoctorByIdUseCase
import ru.mirea.dentalclinic.presentation.common.formatters.DoctorFormatter
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import ru.mirea.dentalclinic.presentation.doctorpage.formatters.ProcedureFormatter
import ru.mirea.dentalclinic.presentation.doctorpage.models.ProcedureVO

class DoctorPageViewModel @AssistedInject constructor(
    @Assisted val doctorId: Long,
    private val getDoctorByIdUseCase: GetDoctorByIdUseCase,
    private val doctorFormatter: DoctorFormatter,
    private val procedureFormatter: ProcedureFormatter
) : ViewModel() {
    private val _state = MutableStateFlow<DoctorPageState>(DoctorPageState.Idle)
    val state: StateFlow<DoctorPageState> = _state

    init {
        update()
    }

    fun update() {
        if (state.value is DoctorPageState.Loading) {
            return
        }
        _state.value = DoctorPageState.Loading
        viewModelScope.launch {
            val doctorResult = getDoctorByIdUseCase.execute(doctorId)
            doctorResult.onSuccess { doctor ->
                _state.value = DoctorPageState.Success(
                    doctor = doctorFormatter.format(doctor.doctor),
                    procedures = doctor.procedures.map(procedureFormatter::format)
                )
            }.onFailure { throwable ->
                _state.value = DoctorPageState.Error(throwable.message ?: "")
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(doctorId: Long): DoctorPageViewModel
    }
}

sealed class DoctorPageState {
    object Idle: DoctorPageState()
    object Loading : DoctorPageState()
    data class Success(val doctor: DoctorVO, val procedures: List<ProcedureVO>) : DoctorPageState()
    data class Error(val error: String) : DoctorPageState()
}