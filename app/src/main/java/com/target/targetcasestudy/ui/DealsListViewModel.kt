package com.target.targetcasestudy.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indisp.core.DispatcherProvider
import com.indisp.core.Result
import com.target.targetcasestudy.core.NetworkFailure
import com.target.targetcasestudy.domain.usecase.GetDealsUseCase
import com.target.targetcasestudy.ui.mapper.PresentableDealMapper
import com.target.targetcasestudy.ui.model.PresentableDeal
import com.target.targetcasestudy.ui.route.DealsRouter
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DealsListViewModel(
    private val getDealsUseCase: GetDealsUseCase,
    private val mapper: PresentableDealMapper,
    private val router: DealsRouter,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {
    private companion object {
        val INITIAL_STATE = State (isLoading = true, dealsList = persistentListOf())
    }
    private val _screenStateFlow = MutableStateFlow(INITIAL_STATE)
    val screenStateFlow = _screenStateFlow.asStateFlow()
    private val _sideEffectFlow = MutableStateFlow<SideEffect>(SideEffect.Idle)
    val sideEffectFlow = _sideEffectFlow.asStateFlow()

    private fun fetchDealsList() = viewModelScope.launch(dispatcherProvider.IO) {
        if (screenStateFlow.value.dealsList.isNotEmpty())
            return@launch

        Log.d("PRODBUG", "fetchDealsList: ")
        _screenStateFlow.update { it.copy(isLoading = true) }
        when (val result = getDealsUseCase()) {
            is Result.Error -> {
                val errorMessage = when (result.error) {
                    NetworkFailure.ConnectionTimeOut -> "Connection to server timed out. Please try again."
                    NetworkFailure.NoInternet -> "No active internet found. Please check your internet."
                    is NetworkFailure.UnknownFailure -> "Something went wrong. Please try again later."
                }
                _screenStateFlow.update { it.copy(isLoading = false) }
                _sideEffectFlow.update { SideEffect.ShowError(message = errorMessage) }
            }
            is Result.Success -> _screenStateFlow.update { it.copy(isLoading = false, dealsList = mapper.toPresentableDeals(result.data)) }
        }
    }

    private fun resetSideEffect() = _sideEffectFlow.update { SideEffect.Idle }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnDealClicked -> router.navigateToDealDetails(event.id)
            Event.OnErrorShown -> resetSideEffect()
            Event.OnScreenCreated -> fetchDealsList()
        }
    }

    data class State (
        val isLoading: Boolean,
        val dealsList: PersistentList<PresentableDeal>
    )

    sealed interface Event {
        object OnScreenCreated: Event
        object OnErrorShown: Event
        data class OnDealClicked(val id: Int): Event
    }

    sealed interface SideEffect {
        object Idle: SideEffect
        data class ShowError(val message: String): SideEffect
    }
}