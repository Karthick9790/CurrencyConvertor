package com.app.currencyconvertor.network

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient

fun OkHttpClient.Builder.setSSlCertificate() {
    val serverHostname = "api.apilayer.com"
    val certificatePinner = CertificatePinner.Builder()
        .add(serverHostname, "sha256/jKx/K3o0EaswUsjM80WKm89ukEkIH8ZVlVJ6daRaIyg=")
        .build()
    certificatePinner(certificatePinner)
}