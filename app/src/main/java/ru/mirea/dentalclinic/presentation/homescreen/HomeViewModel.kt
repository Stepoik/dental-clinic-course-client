package ru.mirea.dentalclinic.presentation.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.usecases.GetBestDoctorsUseCase
import ru.mirea.dentalclinic.domain.usecases.GetProceduresUseCase
import ru.mirea.dentalclinic.presentation.common.formatters.DoctorFormatter
import ru.mirea.dentalclinic.presentation.homescreen.formatters.ProcedureFormatter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getBestDoctorsUseCase: GetBestDoctorsUseCase,
    private val getProceduresUseCase: GetProceduresUseCase,
    private val doctorFormatter: DoctorFormatter,
    private val procedureFormatter: ProcedureFormatter
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    private var isFirstSubscribe = true
    val state: StateFlow<HomeScreenState>
        get() = _state.onStart {
            if (isFirstSubscribe) {
                update()
                isFirstSubscribe = !isFirstSubscribe
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, HomeScreenState())

    fun update() {
        if (state.value.isLoading) {
            return
        }
        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }
        viewModelScope.launch {
            val doctorsJob = async { getBestDoctorsUseCase.execute() }
            val procedureJob = async { getProceduresUseCase.execute() }
            val doctorsPage = doctorsJob.await().onError { return@launch }
            val procedures = procedureJob.await().onError { return@launch }
            _state.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    error = null,
                    bestDoctors = doctorsPage.doctors.map(doctorFormatter::format),
                    procedures = procedures.map(procedureFormatter::format)
                )
            }
        }
    }

    private inline fun <R, T : R> Result<T>.onError(body: (Throwable) -> R): R {
        return getOrElse {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    error = it.message
                )
            }
            return body.invoke(it)
        }
    }
}
