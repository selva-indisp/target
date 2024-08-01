package com.target.targetcasestudy.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indisp.core.DispatcherProvider
import com.indisp.core.Result
import com.target.targetcasestudy.core.NetworkFailure
import com.target.targetcasestudy.domain.usecase.GetDealDetailsUseCase
import com.target.targetcasestudy.ui.mapper.PresentableDealMapper
import com.target.targetcasestudy.ui.model.PresentableDeal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DealDetailViewModel(
    private val getDealDetailsUseCase: GetDealDetailsUseCase,
    private val mapper: PresentableDealMapper,
    private val dealId: Int,
    private val dispatcherProvider: DispatcherProvider
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
                val errorMessage = when (result.error) {
                    NetworkFailure.ConnectionTimeOut -> "Connection to server timed out. Please try again."
                    NetworkFailure.NoInternet -> "No active internet found. Please check your internet."
                    is NetworkFailure.UnknownFailure -> "Something went wrong. Please try again later."
                }
                _screenStateFlow.update { it.copy(isLoading = false) }
                _sideEffectFlow.update { SideEffect.ShowError(message = errorMessage) }
            }
            is Result.Success -> {
                Log.d("PRODBUG", "fetchDealDetails: $dealId -> ${result.data}")
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