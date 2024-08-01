package com.target.deal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.core.DispatcherProvider
import com.target.core.Result
import com.target.deal.R
import com.target.core.NetworkFailure
import com.target.core.ResourceProvider
import com.target.deal.domain.usecase.GetDealDetailsUseCase
import com.target.deal.ui.mapper.PresentableDealMapper
import com.target.deal.ui.model.PresentableDeal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DealDetailViewModel(
    private val getDealDetailsUseCase: GetDealDetailsUseCase,
    private val mapper: PresentableDealMapper,
    private val dealId: Int,
    private val dispatcherProvider: DispatcherProvider,
    private val resourceProvider: ResourceProvider
): ViewModel() {
    private companion object {
        val INITIAL_STATE = State (isLoading = true, deal = null)
    }
    private val _screenStateFlow = MutableStateFlow(INITIAL_STATE)
    val screenStateFlow = _screenStateFlow.asStateFlow()
    private val _sideEffectFlow = MutableStateFlow<SideEffect>(SideEffect.Idle)
    val sideEffectFlow = _sideEffectFlow.asStateFlow()

    private fun fetchDealDetails() = viewModelScope.launch(dispatcherProvider.IO) {
        if (_screenStateFlow.value.deal != null)
            return@launch

        _screenStateFlow.update { it.copy(isLoading = true) }
        when (val result = getDealDetailsUseCase(dealId)) {
            is Result.Error -> {
                val errorMessageId = when (result.error) {
                    NetworkFailure.ConnectionTimeOut -> R.string.connection_timedout
                    NetworkFailure.NoInternet -> R.string.no_internet
                    is NetworkFailure.UnknownFailure -> R.string.unknown_error
                }
                _screenStateFlow.update { it.copy(isLoading = false) }
                _sideEffectFlow.update { SideEffect.ShowError(message = resourceProvider.getString(errorMessageId)) }
            }
            is Result.Success -> {
                _screenStateFlow.update { it.copy(isLoading = false, deal = mapper.toPresentableDeal(result.data)) }
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.OnErrorShown -> _sideEffectFlow.update { SideEffect.Idle }
            Event.OnScreenCreated -> fetchDealDetails()
        }
    }

    data class State (
        val isLoading: Boolean,
        val deal: PresentableDeal?
    )

    sealed interface Event {
        object OnScreenCreated: Event
        object OnErrorShown: Event
    }

    sealed interface SideEffect {
        object Idle: SideEffect
        data class ShowError(val message: String): SideEffect
    }
}