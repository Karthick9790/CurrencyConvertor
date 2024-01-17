package com.app.currencyconvertor.data.repository

import com.app.currencyconvertor.data.model.RateResponse
import com.app.currencyconvertor.data.service.CurrencyService
import com.app.currencyconvertor.domain.repository.ICurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyService: CurrencyService,
) : ICurrencyRepository {

    override fun getSymbols(): Flow<List<String>> {
        return flow {
            val response = currencyService.getSymbols()
            if (response.isSuccessful) {
                val body = response.body()?.string() ?: throw Exception()
                val symbolsJson = JSONObject(body).optJSONObject("symbols")
                val symbols = mutableListOf<String>()
                symbolsJson.keys().forEach {
                    symbols.add(it)
                }
                emit(symbols)
            } else {
                throw Exception()
            }
        }
    }

    override fun convertKWDRates(
        amount: Double,
        base: String,
        toCurrencies: String
    ): Flow<List<RateResponse>> {
        return flow {
            val response = currencyService.convertKWDRates(
                base = base,
                symbols = toCurrencies,
            )
            if (response.isSuccessful) {
                val body = response.body()?.string() ?: throw Exception()
                val ratesJson = JSONObject(body).optJSONObject("rates")
                val symbols = mutableListOf<RateResponse>()
                ratesJson.keys().forEach {
                    symbols.add(RateResponse(
                        rate = (ratesJson.optDouble(it, 0.0)).toBigDecimal(),
                        currency = it
                    ))
                }
                emit(symbols)
            }
        }
    }
}