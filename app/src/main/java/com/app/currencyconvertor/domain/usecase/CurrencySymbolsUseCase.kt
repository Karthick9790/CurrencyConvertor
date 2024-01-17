package com.app.currencyconvertor.domain.usecase

import com.app.currencyconvertor.domain.repository.ICurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CurrencySymbolsUseCase @Inject constructor(
    private val iCurrencyRepository: ICurrencyRepository
) {
    fun invoke(): Flow<List<String>> {
        return iCurrencyRepository.getSymbols()
            .flowOn(Dispatchers.IO)
    }
}