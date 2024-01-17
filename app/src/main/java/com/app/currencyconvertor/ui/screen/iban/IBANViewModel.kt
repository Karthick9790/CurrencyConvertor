package com.app.currencyconvertor.ui.screen.iban

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.currencyconvertor.domain.usecase.ValidateIBANUseCase
import com.app.currencyconvertor.ui.ScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IBANViewModel @Inject constructor(
    private val ibanUseCase: ValidateIBANUseCase
): ViewModel() {

    private val _ibanState = MutableStateFlow<ScreenUIState>(ScreenUIState.Loading)
    val ibanState = _ibanState.asStateFlow()

    fun validateIBAN(blzCode: String) {
        viewModelScope.launch {
            ibanUseCase.invoke(blzCode)
                .onEach {
                    _ibanState.value = ScreenUIState.Response(it)
                }
                .catch {
                    _ibanState.value = ScreenUIState.Error(it.message ?: "")
                }
                .collect()
        }
    }
}