package com.app.currencyconvertor.di

import com.app.currencyconvertor.BuildConfig
import com.app.currencyconvertor.data.service.CurrencyService
import com.app.currencyconvertor.data.service.IBANService
import com.app.currencyconvertor.network.AuthInterceptor
import com.app.currencyconvertor.network.setSSlCertificate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesOkHttp(): OkHttpClient {

        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(logging)
            }
            setSSlCertificate()
            addInterceptor(AuthInterceptor())
        }
            .build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesCurrencyService(retrofit: Retrofit): CurrencyService {
        return retrofit.create(CurrencyService::class.java)
    }

    @Provides
    fun providesIBANService(retrofit: Retrofit): IBANService {
        return retrofit.create(IBANService::class.java)
    }
}