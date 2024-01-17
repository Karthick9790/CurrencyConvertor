package com.app.currencyconvertor.di

import com.app.currencyconvertor.data.repository.CurrencyRepository
import com.app.currencyconvertor.data.repository.IBANRepository
import com.app.currencyconvertor.data.service.CurrencyService
import com.app.currencyconvertor.data.service.IBANService
import com.app.currencyconvertor.domain.repository.IBANInterface
import com.app.currencyconvertor.domain.repository.ICurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesRepository(currencyService: CurrencyService): ICurrencyRepository {
        return CurrencyRepository(currencyService)
    }

    @Provides
    fun providesIBANRepository(ibanService: IBANService): IBANInterface {
        return IBANRepository(ibanService)
    }
}