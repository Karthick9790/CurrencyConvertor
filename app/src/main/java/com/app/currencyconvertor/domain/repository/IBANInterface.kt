package com.app.currencyconvertor.domain.repository

import com.app.currencyconvertor.data.model.IBANResponse
import kotlinx.coroutines.flow.Flow

interface IBANInterface {
    fun validate(
        blzCode: String
    ): Flow<IBANResponse>
}