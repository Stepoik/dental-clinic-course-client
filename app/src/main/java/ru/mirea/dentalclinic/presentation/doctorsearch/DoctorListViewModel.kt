package ru.mirea.dentalclinic.presentation.doctorsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mirea.dentalclinic.domain.usecases.SearchDoctorsUseCase
import ru.mirea.dentalclinic.presentation.common.formatters.DoctorFormatter
import ru.mirea.dentalclinic.presentation.common.models.DoctorVO
import javax.inject.Inject

class DoctorListViewModel @Inject constructor(
    private val searchDoctorsUseCase: SearchDoctorsUseCase,
    private val doctorFormatter: DoctorFormatter
) : ViewModel() {
    private var searchJob: Job? = null

    private val _state = MutableStateFlow<DoctorListState>(DoctorListState.Idle)

    private val searchQuery = MutableStateFlow("")

    val state: StateFlow<DoctorListState>
        get() = _state

    init {
        @OptIn(FlowPreview::class)
        searchQuery
            .debounce(200)
            .distinctUntilChanged()
            .onEach { queryAccepted(it) }
            .launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String) {
        searchQuery.update { query }
    }

    fun onNextPage() {
        if (searchJob?.isActive == true) { return }
        searchJob = viewModelScope.launch {
            val currentState = _state.value
            if (currentState is DoctorListState.Success) {
                findDoctor(currentState.query, currentState.page + 1)
            }
        }
    }

    // Начать запрос если закончился ввод
    private fun queryAccepted(query: String = "") {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            findDoctor(query, 0)
        }
    }

    private suspend fun findDoctor(query: String, page: Int) {
        _state.update { DoctorListState.Loading }
        val searchResult = searchDoctorsUseCase.execute(query, page)
        val doctorPage = searchResult.getOrElse {
            _state.update { DoctorListState.Error(it.toString()) }
            return
        }
        _state.update {
            DoctorListState.Success(
                doctors = doctorPage.doctors.map(doctorFormatter::format),
                query = query,
                page = page
            )
        }
    }
}

sealed class DoctorListState {
    object Idle : DoctorListState()
    object Loading : DoctorListState()

    data class Success(val doctors: List<DoctorVO>, val query: String, val page: Int) :
        DoctorListState()

    data class Error(val error: String) : DoctorListState()
}