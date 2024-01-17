package com.app.currencyconvertor.network

import com.app.currencyconvertor.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("apikey", BuildConfig.APP_KEY)
        return chain.proceed(requestBuilder.build())
    }
}