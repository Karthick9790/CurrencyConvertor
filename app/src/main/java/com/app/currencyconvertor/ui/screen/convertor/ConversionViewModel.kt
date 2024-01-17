package com.app.currencyconvertor.ui.screen.convertor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.currencyconvertor.data.model.RateResponse
import com.app.currencyconvertor.domain.usecase.CurrencySymbolsUseCase
import com.app.currencyconvertor.domain.usecase.KWDRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversionViewModel @Inject constructor(
    private val currencySymbolsUseCase: CurrencySymbolsUseCase,
    private val kwdRatesUseCase: KWDRatesUseCase
) : ViewModel() {

    private val _currencyState = MutableStateFlow<UIState>(UIState.Loading)
    val currencyState = _currencyState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getKWDRates(currencyCode: String) {
        viewModelScope.launch {
            currencySymbolsUseCase.invoke()
                .map {
                    it.joinToString(separator = ",")
                }
                .flatMapLatest {
                    kwdRatesUseCase.invoke(1.0, toCurrencies = it, base = currencyCode)
                }
                .onEach {
                    _currencyState.value = UIState.CurrencyList(it)
                }
                .catch {
                    _currencyState.value = UIState.Error(it.message ?: "")
                }
                .collect()
        }
    }
}

sealed class UIState {
    object Loading : UIState()
    class CurrencyList(
        val list: List<RateResponse>
    ) : UIState()

    class Error(val message: String) : UIState()
}