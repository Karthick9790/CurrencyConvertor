package com.app.currencyconvertor.data.model

import com.google.gson.annotations.SerializedName

data class IBANResponse(
    @SerializedName("valid")
    val valid: Boolean,
    @SerializedName("blz_code")
    val code: String,
    @SerializedName("bank_data")
    val bankData: BankData
) {
    data class BankData(
        @SerializedName("bic")
        val bic: String,
        @SerializedName("city")
        val city: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("zip")
        val zip: String
    )
}
