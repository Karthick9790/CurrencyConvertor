package com.app.currencyconvertor.ui.screen

import com.app.currencyconvertor.MainCoroutineRule
import com.app.currencyconvertor.domain.repository.ICurrencyRepository
import com.app.currencyconvertor.ui.screen.convertor.ConversionViewModel
import com.app.currencyconvertor.ui.screen.convertor.UIState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConversionViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @MockK
    lateinit var iCurrencyRepository: ICurrencyRepository

    private lateinit var conversionViewModel: ConversionViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        conversionViewModel = spyk(ConversionViewModel(iCurrencyRepository))
    }

    @Test
    fun `test getLatestRates`() = runBlockingTest {

        val result = mutableListOf<UIState>()
        every { iCurrencyRepository.getLatestRates("USD") } returns flow {
            emit(LinkedHashMap())
        }
        val job = launch {
            conversionViewModel.currencyState.toList(result)
        }

        conversionViewModel.getLatestRates("USD")

        assert(result[0] is UIState.Loading)
        assert(result[1] is UIState.CurrencyList)

        job.cancel()
    }

    @Test
    fun `test getLatestRates exception`() = runBlockingTest {

        val result = mutableListOf<UIState>()
        every { iCurrencyRepository.getLatestRates("USD") } returns flow {
            throw Exception()
        }
        val job = launch {
            conversionViewModel.currencyState.toList(result)
        }

        conversionViewModel.getLatestRates("USD")

        assert(result[0] is UIState.Loading)
        assert(result[1] is UIState.Error)

        job.cancel()
    }

    @Test
    fun `test convert`() = runBlockingTest {
        val result = mutableListOf<UIState>()

        conversionViewModel.usdRates = LinkedHashMap<String, Double>().apply {
            put("USD", 1.0)
            put("INR", 83.0)
        }
        val job = launch {
            conversionViewModel.currencyState.toList(result)
        }

        conversionViewModel.convert(1.0, "INR")

        assert(result[0] is UIState.Loading)
        Assert.assertEquals((result[1] as UIState.CurrencyList).list["USD"], 0.01)
        Assert.assertEquals((result[1] as UIState.CurrencyList).list["INR"], 1.0)

        job.cancel()
    }
}