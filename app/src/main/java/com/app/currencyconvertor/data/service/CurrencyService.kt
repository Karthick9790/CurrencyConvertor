package com.app.currencyconvertor.data.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("/fixer/symbols")
    suspend fun getSymbols(): Response<ResponseBody>

    @GET("fixer/latest")
    suspend fun convertKWDRates(
        @Query("base") base: String,
        @Query("symbols") symbols: String,
    ): Response<ResponseBody>
}