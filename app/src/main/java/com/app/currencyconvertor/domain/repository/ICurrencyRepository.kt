package com.app.currencyconvertor.domain.repository

import com.app.currencyconvertor.data.model.RateResponse
import kotlinx.coroutines.flow.Flow

interface ICurrencyRepository {
    fun getSymbols(): Flow<List<String>>
    fun convertKWDRates(
        amount: Double,
        base: String,
        toCurrencies: String
    ): Flow<List<RateResponse>>
}