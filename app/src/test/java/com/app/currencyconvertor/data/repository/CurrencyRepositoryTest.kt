package com.app.currencyconvertor.data.repository

import com.app.currencyconvertor.data.service.CurrencyService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.lang.Exception

class CurrencyRepositoryTest {

    @MockK
    lateinit var iAppStorage: IAppStorage

    @MockK
    lateinit var currencyService: CurrencyService

    private lateinit var currencyRepository: CurrencyRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        currencyRepository = spyk(CurrencyRepository(currencyService, iAppStorage))
    }

    @Test
    fun `test getLatestRates`() = runBlocking {
        val response = Response.success(response.toResponseBody())
        val result = mutableListOf<LinkedHashMap<String, Double>>()

        coEvery { currencyService.getLatestRates(any(), any()) } returns response
        every { iAppStorage.getData() } returns ""
        every { iAppStorage.getSavedTime() } returns 0L
        every { iAppStorage.saveTime(any()) } returns Unit
        every { iAppStorage.saveData(any()) } returns Unit

        val job = launch {
            currencyRepository.getLatestRates("\"USD\"").toList(result)
        }

        coVerify { currencyService.getLatestRates(any(), any()) }
        verify { currencyRepository.parseResponse(this@CurrencyRepositoryTest.response) }

        job.cancel()
    }

    @Test
    fun `test getLatestRates cache`() = runBlocking {
        val response = Response.success(response.toResponseBody())
        val result = mutableListOf<LinkedHashMap<String, Double>>()

        coEvery { currencyService.getLatestRates(any(), any()) } returns response
        every { iAppStorage.getData() } returns this@CurrencyRepositoryTest.response
        every { iAppStorage.getSavedTime() } returns System.currentTimeMillis()
        every { iAppStorage.saveTime(any()) } returns Unit
        every { iAppStorage.saveData(any()) } returns Unit

        val job = launch {
            currencyRepository.getLatestRates("\"USD\"").toList(result)
        }

        coVerify(exactly = 0) { currencyService.getLatestRates(any(), any()) }
        verify { currencyRepository.parseResponse(this@CurrencyRepositoryTest.response) }

        job.cancel()
    }

    @Test
    fun `test parseResponse with null data`() {
        Assert.assertTrue(currencyRepository.parseResponse(null).isEmpty())
    }

    @Test
    fun `test handleResponse`() {
        val response = Response.success(response.toResponseBody())
        every { iAppStorage.saveTime(any()) } returns Unit
        every { iAppStorage.saveData(any()) } returns Unit
        every { currencyRepository.parseResponse(any()) } returns LinkedHashMap()

        currencyRepository.handleResponse(response = response)

        verify { iAppStorage.saveTime(any()) }
        verify { currencyRepository.parseResponse(any()) }
    }

    @Test(expected = Exception::class)
    fun `test handleResponse with generic exception`() {
        val response = Response.error<ResponseBody>(400, "".toResponseBody())

        every { currencyRepository.parseResponse(any()) } returns LinkedHashMap()

        currencyRepository.handleResponse(response = response)

        verify(exactly = 0) { iAppStorage.saveTime(any()) }
        verify(exactly = 0) { currencyRepository.parseResponse(any()) }
    }

    private val response = "{\n" +
            "  \"timestamp\": 1701676800,\n" +
            "  \"base\": \"USD\",\n" +
            "  \"rates\": {\n" +
            "    \"AED\": 3.6724,\n" +
            "    \"AFN\": 69.982617,\n" +
            "    \"ALL\": 93.123143\n" +
            "  }\n" +
            "}"
}