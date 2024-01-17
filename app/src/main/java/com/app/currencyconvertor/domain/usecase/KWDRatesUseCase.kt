package com.app.currencyconvertor.domain.usecase

import com.app.currencyconvertor.data.model.RateResponse
import com.app.currencyconvertor.domain.repository.ICurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class KWDRatesUseCase @Inject constructor(
    private val iCurrencyRepository: ICurrencyRepository
) {
    fun invoke(
        amount: Double,
        base: String,
        toCurrencies: String
    ): Flow<List<RateResponse>> {
        return iCurrencyRepository.convertKWDRates(
            base = base,
            amount = amount,
            toCurrencies = toCurrencies
        )
            .flowOn(Dispatchers.IO)
    }
}