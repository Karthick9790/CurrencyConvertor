package com.app.currencyconvertor.data.repository

import com.app.currencyconvertor.data.model.IBANResponse
import com.app.currencyconvertor.data.service.IBANService
import com.app.currencyconvertor.domain.repository.IBANInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IBANRepository @Inject constructor(
    private val ibanService: IBANService
): IBANInterface {

    override fun validate(blzCode: String): Flow<IBANResponse> {
        return flow {
            emit(ibanService.validate(blzCode = blzCode))
        }
    }
}