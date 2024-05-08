package ru.mirea.dentalclinic.presentation.proceduredoctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.usecases.GetDoctorsByProcedureNameUseCase
import ru.mirea.dentalclinic.presentation.common.formatters.DoctorFormatter
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO

class ProcedureDoctorsViewModel @AssistedInject constructor(
    @Assisted private val procedureName: String,
    private val getDoctorsByProcedureNameUseCase: GetDoctorsByProcedureNameUseCase,
    private val doctorFormatter: DoctorFormatter
) : ViewModel() {
    private val _state = MutableStateFlow<ProcedureDoctorsState>(ProcedureDoctorsState.Idle(procedureName = procedureName))
    val state: StateFlow<ProcedureDoctorsState> get() = _state

    fun update() {
        _state.value = ProcedureDoctorsState.Loading(procedureName)
        viewModelScope.launch {
            val doctorsResult = getDoctorsByProcedureNameUseCase.execute(procedureName)
            doctorsResult.onSuccess { doctors ->
                _state.value = ProcedureDoctorsState.Success(
                    procedureName = procedureName,
                    doctors = doctors.map(doctorFormatter::format)
                )
            }.onFailure { exception ->
                _state.value = ProcedureDoctorsState.Error(
                    procedureName = procedureName,
                    message = exception.message ?: ""
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(procedureId: String): ProcedureDoctorsViewModel
    }
}

sealed interface ProcedureDoctorsState {
    val procedureName: String

    data class Idle(override val procedureName: String) : ProcedureDoctorsState
    data class Loading(override val procedureName: String) : ProcedureDoctorsState
    data class Error(override val procedureName: String, val message: String) :
        ProcedureDoctorsState

    data class Success(override val procedureName: String, val doctors: List<DoctorVO>) :
        ProcedureDoctorsState
}