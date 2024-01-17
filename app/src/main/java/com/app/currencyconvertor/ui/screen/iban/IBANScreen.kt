package com.app.currencyconvertor.ui.screen.iban

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.currencyconvertor.data.model.IBANResponse
import com.app.currencyconvertor.ui.ScreenUIState
import com.app.currencyconvertor.ui.components.AppError
import com.app.currencyconvertor.ui.components.AppProgress

@Composable
fun IBANScreen(
    ibanViewModel: IBANViewModel = hiltViewModel()
) {

    val uiState by ibanViewModel.ibanState.collectAsState()

    var iban by remember {
        mutableStateOf("")
    }

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            OutlinedTextField(
                value = iban,
                onValueChange = { if (it.length < 9) iban = it },
                label = { Text("Enter IBAN number") },
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                supportingText = {
                    Text(
                        text = "${iban.length} / 8",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                }
            )

            AnimatedVisibility(visible = iban.length == 8) {
                Button(
                    onClick = {
                        ibanViewModel.validateIBAN(iban)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit")
                }
            }

            (uiState as? ScreenUIState.Response<IBANResponse>)?.let {
                Text(
                    text = if (it.data.valid) "IBAN valid" else  "IBAN invalid"
                )
            }
        }

        AppProgress(
            isVisible = (uiState is ScreenUIState.Loading && iban.isNotEmpty()),
            modifier = Modifier.align(Alignment.Center)
        )

        AppError(
            isVisible = uiState is ScreenUIState.Error,
            message = (uiState as? ScreenUIState.Error)?.message ?: "",
            modifier = Modifier.align(Alignment.Center)
        )
    }


}