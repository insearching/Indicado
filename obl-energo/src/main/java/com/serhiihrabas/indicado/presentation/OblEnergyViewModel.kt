package com.serhiihrabas.indicado.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiihrabas.core.data.network.NetworkResult
import com.serhiihrabas.indicado.domain.OblEnergoRepository
import com.serhiihrabas.indicado.domain.PowerIndicator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OblEnergyViewModel(
    private val repository: OblEnergoRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(OblEnergyState())
    val state = _state.asStateFlow()

    private val _events = Channel<OblEnergyEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: OblEnergyAction) {
        when (action) {
            is OblEnergyAction.OnAccountNumberChange -> {
                _state.update { it.copy(accountNumber = action.accountNumber) }
            }

            is OblEnergyAction.OnLastNameChange -> {
                _state.update { it.copy(lastName = action.lastName) }
            }

            is OblEnergyAction.OnIndicatorValueChange -> {
                _state.update { it.copy(indicatorValue = action.indicatorValue) }
            }

            is OblEnergyAction.OnDateChange -> {
                _state.update { it.copy(date = action.date) }
            }

            is OblEnergyAction.OnSendIndicator -> {
                sendIndicator()
            }
        }
    }

    private fun sendIndicator() {
        val accountNumber = state.value.accountNumber
        val lastName = state.value.lastName
        val indicatorValue = state.value.indicatorValue
        val date = state.value.date

        if (accountNumber.isBlank() || lastName.isBlank() || indicatorValue.isBlank()) {
            return
        }

        val powerIndicator = PowerIndicator(
            personalNumber = accountNumber,
            lastName = lastName,
            indicatorValue = indicatorValue,
            date = date
        )

        viewModelScope.launch {
            val result = repository.sendPowerIndicator(powerIndicator)
            if (result is NetworkResult.Success) {
                _events.send(OblEnergyEvent.ShowMessage("Показ успішно відправлено"))
            } else if (result is NetworkResult.Error) {
                _events.send(OblEnergyEvent.ShowMessage(result.message))
            }
        }
    }
}