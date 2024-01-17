package com.app.currencyconvertor

import android.content.Context
import okio.buffer
import okio.source
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset


object AppUtil {

    private var currencyProperty: JSONObject? = null
    private const val FILE_NAME = "currencies.json"

    fun loadCurrencyProperty(context: Context) {
        try {
            val source = context.assets.open(FILE_NAME).source().buffer()
            currencyProperty = JSONObject(source.readByteString().string(Charset.forName("utf-8")))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getCurrencyCountry(key: String) = currencyProperty?.optString(key)
}