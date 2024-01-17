package com.app.currencyconvertor.ui

sealed class ScreenUIState {
    object Loading : ScreenUIState()
    class Response<T>(
        val data: T
    ) : ScreenUIState()

    class Error(val message: String) : ScreenUIState()
}