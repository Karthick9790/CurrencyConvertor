package com.app.currencyconvertor.ui.screen.convertor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.currencyconvertor.data.model.RateResponse
import com.app.currencyconvertor.ui.components.AppError
import com.app.currencyconvertor.ui.components.AppProgress

@Composable
fun ConversionScreen(
    conversionViewModel: ConversionViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        conversionViewModel.getKWDRates("KWD")
    }
    val uiState by conversionViewModel.currencyState.collectAsState()

    var baseRate by remember {
        mutableStateOf("1.0")
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AnimatedVisibility(visible = uiState is UIState.CurrencyList) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = "KWD conversion rates",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = baseRate,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onValueChange = {
                            baseRate = it
                        },
                        textStyle = TextStyle(textDirection = TextDirection.Rtl, fontSize = 26.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                item {
                    Spacer(modifier = Modifier.size(8.dp))
                }

                item {
                    Button(
                        onClick = {
                            baseRate.toDoubleOrNull()?.let {

                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Convert",
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                when (uiState) {
                    is UIState.CurrencyList -> {
                        val list = (uiState as UIState.CurrencyList).list
                        items(list) {
                            RatesComponent(rate = it)
                        }
                    }

                    else -> Unit
                }
            }
        }

        AppProgress(
            isVisible = uiState is UIState.Loading,
            modifier = Modifier.align(Alignment.Center)
        )

        AppError(
            isVisible = uiState is UIState.Error,
            message = (uiState as? UIState.Error)?.message ?: "",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun RatesComponent(rate: RateResponse) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rate.currency,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(fraction = 0.5f)
        )

        Text(
            text = rate.rate.toString(),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(fraction = 0.5f)
        )
    }
}