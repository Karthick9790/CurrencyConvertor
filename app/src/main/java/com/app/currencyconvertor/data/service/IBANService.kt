package com.app.currencyconvertor.data.service

import com.app.currencyconvertor.data.model.IBANResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IBANService {
    @GET("/bank_data/check_blz_code")
    suspend fun validate(
        @Query("blz_code") blzCode: String
    ): IBANResponse
}