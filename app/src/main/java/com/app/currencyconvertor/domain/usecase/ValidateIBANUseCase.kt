package com.app.currencyconvertor.domain.usecase

import com.app.currencyconvertor.data.model.IBANResponse
import com.app.currencyconvertor.domain.repository.IBANInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ValidateIBANUseCase @Inject constructor(
    private val ibanInterface: IBANInterface
) {
    fun invoke(blzCode: String): Flow<IBANResponse> {
        return ibanInterface.validate(blzCode)
            .flowOn(Dispatchers.IO)
    }
}