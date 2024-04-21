package ru.mirea.dentalclinic.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.usecases.GetBestDoctorsUseCase
import ru.mirea.dentalclinic.domain.usecases.GetProceduresUseCase
import ru.mirea.dentalclinic.homescreen.formatters.DoctorFormatter
import ru.mirea.dentalclinic.homescreen.formatters.ProcedureFormatter
import javax.inject.Inject
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class HomeViewModel @Inject constructor(
    private val getBestDoctorsUseCase: GetBestDoctorsUseCase,
    private val getProceduresUseCase: GetProceduresUseCase,
    private val doctorFormatter: DoctorFormatter,
    private val procedureFormatter: ProcedureFormatter
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> get() = _state

    init {
        update()
    }

    fun update() {
        if (state.value.isLoading) {
            return
        }
        _state.value = _state.value.copy(
            isLoading = true,
            error = null
        )
        viewModelScope.launch {
            val doctorsJob = async { getBestDoctorsUseCase.execute() }
            val procedureJob = async { getProceduresUseCase.execute() }
            val doctors = doctorsJob.await().onError { return@launch }
            val procedures = procedureJob.await().onError { return@launch }
            _state.value = _state.value.copy(
                isLoading = false,
                error = null,
                bestDoctors = doctors.map(doctorFormatter::format),
                procedures = procedures.map(procedureFormatter::format)
            )
        }
    }

    private inline fun <R, T : R> Result<T>.onError(body: (Throwable) -> R): R {
        return getOrElse {
            _state.value = _state.value.copy(
                isLoading = false,
                error = it.message
            )
            return body.invoke(it)
        }
    }
}
