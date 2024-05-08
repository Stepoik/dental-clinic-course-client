package ru.mirea.dentalclinic.presentation.procedures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.usecases.GetProceduresUseCase
import ru.mirea.dentalclinic.presentation.common.models.ProcedureFormatter
import ru.mirea.dentalclinic.presentation.common.models.ProcedureVO
import javax.inject.Inject

class ProceduresViewModel @Inject constructor(
    private val getProceduresUseCase: GetProceduresUseCase,
    private val procedureFormatter: ProcedureFormatter
): ViewModel() {
    private val _state = MutableStateFlow<ProceduresState>(ProceduresState.Idle)
    val state: StateFlow<ProceduresState> get() = _state

    fun update() {
        _state.value = ProceduresState.Loading
        viewModelScope.launch {
            val procedureResult = getProceduresUseCase.execute()
            procedureResult.onSuccess { procedures ->
                _state.value = ProceduresState.Success(procedures.map(procedureFormatter::format))
            }.onFailure { exception ->
                _state.value = ProceduresState.Error(exception.message ?: "")
            }
        }
    }
}

sealed class ProceduresState {
    object Idle: ProceduresState()
    object Loading: ProceduresState()
    data class Error(val message: String): ProceduresState()
    data class Success(val procedures: List<ProcedureVO>): ProceduresState()
}