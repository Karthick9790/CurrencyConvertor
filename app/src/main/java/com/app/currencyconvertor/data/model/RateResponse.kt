package com.app.currencyconvertor.data.model

import java.math.BigDecimal

data class RateResponse(
    val rate: BigDecimal,
    val currency: String
)
